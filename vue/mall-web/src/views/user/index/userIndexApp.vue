<template>
<div class="max-width-wrapper">
    
    <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
    <TopUserSpan />     
    <div style="clear:both"></div>

    <TopBannerSearch />
    <div style="clear:both"></div>

    <div class='g-box-wrapper' style="margin-bottom:100px;">

	<!-- ========= 左侧树形菜单 begin ===================== -->
        <div class='g-content-left'>
                <div class='top-title-1'>
                        个人中心
                </div>
                <div v-for="parent in menuList" :key="'pm-' + parent.name">
                    <div class='l-sep'>&nbsp;</div>
                    <div class='l-row-title'>{{parent.name}}</div>

                    <div class='l-row-content' 
                        v-bind:class="child.select == true ? 'active': ''"
                        @click="_changeSelect(child)"
                        v-for="child in parent.children" :key="'menu-' + child.name">
                        {{child.name}}
                    </div>

                </div>
        </div>
        <!-- ========= 左侧树形菜单 end ===================== -->


        <!-- ========= 右侧内容区 begin ===================== -->
        <template v-if="_selectMenu == '我的订单'">
            <MyOrder/>
        </template>
        <template v-if="_selectMenu == '安全设置'">
            <UserSecurity/>
        </template>
        <template v-if="_selectMenu == '关注商品'">
            <UserLikeGoods/>
        </template>

        <!-- ========= 右侧内容区 end ===================== -->


    
        
    </div>

    <BottomArticle />
    <div style="clear:both"></div>

    <BottomLink />
    <div style="clear:both"></div>

</div>
</template>

<script>

    import Vue from 'vue'
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import TopBannerSearch from '@/components/biz/TopBannerSearch';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import store from '@/store'
    
    import * as Ajax from '@/helper/Ajax';
    import * as Urls from '@/helper/Urls';
    import * as Events from '@/helper/Events';
    import * as VueHelper from '@/helper/VueHelper';

    import MyOrder from './MyOrder';
    import UserSecurity from './UserSecurity';
    import UserLikeGoods from './UserLikeGoods';
    

export default {
    store, 
  data() {
    return {

        menuList : [
            {'name': '我的交易', children: [
                {'name': '我的订单', select: true}
            ]}
            ,{'name': '我的关注', children: [
                {'name': '关注商品', select: false}
            ]}
            ,{'name': '个人设置', children: [
                // {'name': '个人信息', select: false}
                // ,{'name': '收货地址', select: false}
                {'name': '安全设置', select: false}
            ]}
        ], 
                
    }
  },
  components: {
        TopUserSpan , BottomArticle, TopBannerSearch
        , BottomLink, MyOrder, UserSecurity, UserLikeGoods
      
  },
  computed: {
      _selectMenu(){
          let menu = null;
          this.menuList.forEach(parent => {
              parent.children.forEach(child => {
                  if(child.select){
                      menu = child.name;
                  }
              });
          });
          return menu;
      }, 
  }, 
  updated(){
      
  },
  watch:{
  },
  //相关操作事件
  methods: {


        _changeSelect(item){

            let inst = this;
            let callback = function(){
                inst.menuList.forEach(parent => {
                    VueHelper.setPropValue(parent.children, 'select', false);                
                });
                item.select = true;
            }

            this.$root.Bus.$emit(Events.NEED_MODAL_LOGIN, callback);

        }, 

        _initAndMustLogin(){
                
            let inst = this;
            let callback = function(){
                inst.$root.Bus.$emit(Events.CHILD_INIT);
            }

            this.$root.Bus.$emit(Events.NEED_MODAL_LOGIN, callback);

        }, 
  }, 
  mounted(){
      
      this._initAndMustLogin();
      
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';


    .g-content-left{
        float: left;
        width: 200px;
        font-size:16px;
        line-height: 24px;
        .top-title-1{
            height: 45px;
            line-height: 45px;
            background: #f8f8f8;
            font-size: 16px;
            padding-left: 60px;
            color: #333;
        }
        /** 左侧菜单 **/
        .l-row-title{
            font-size: 14px;
            padding-left: 60px;
            font-weight: bold;
        }
        .l-row-content{
        color: #666;
        padding-left: 60px;
        display: block;
        cursor: pointer;
          &.active {
              color: $g-main-color;
            }
        
        }
         
        .l-sep{
          line-height: 14px;
        }
    }
   
   
</style>
