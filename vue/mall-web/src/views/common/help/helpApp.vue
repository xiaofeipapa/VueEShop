<template>
<div class="max-width-wrapper">
    
    <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
    <TopUserSpan />     
    <div style="clear:both"></div>

    <TopBannerSearch />
    <div style="clear:both"></div>

    <div class='g-box-wrapper'>
	   

	<!-- ========= 左侧树形菜单 begin ===================== -->
        <div class='g-content-left'>
                <div class='top-title-1'>
                        个人中心
                </div>
                <div v-for="parent in helpList" :key="'pm-' + parent.name">
                    <div class='l-sep'>&nbsp;</div>
                    <div class='l-row-title'>{{parent.name}}</div>

                    <div class='l-row-content' 
                        v-bind:class="child.select == true ? 'active': ''"
                        @click="_clickSelect(child)"
                        v-for="child in parent.children" :key="'menu-' + child.name">
                        {{child.name}}
                    </div>

                </div>
        </div>
        <!-- ========= 左侧树形菜单 end ===================== -->
        <div class='g-content-right' v-html="content">
        </div>
        <div style="clear:both"></div>


    
        
    </div>
    
    <BottomLink />
    <div style="clear:both"></div>

</div>
</template>

<script>

    import Vue from 'vue'
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import TopBannerSearch from '@/components/biz/TopBannerSearch';
    import BottomLink from '@/components/biz/BottomLink';
    import LoadingScreen from 'components/com/LoadingScreen';
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import store from '@/store'
    
    import * as Ajax from '@/helper/Ajax';
    import * as Urls from '@/helper/Urls';
    import * as Events from '@/helper/Events';
    import * as VueHelper from '@/helper/VueHelper';
    

export default {
    store, 
  data() {
    return {

        helpList: [], 

        content: '', 
                
    }
  },
  components: {
        TopUserSpan , TopBannerSearch
        , BottomLink, LoadingScreen, 
      
  },
  computed: {
      
  }, 
  updated(){
      
  },
  watch:{
  },
  //相关操作事件
  methods: {

      _clickSelect(menu){
          this.content = menu.content;
      }, 

    _loadData() {
        let inst = this;

        let url = Urls.common_getHelpList;
        let params = {};

        Ajax.post(url, params, function(serverData) {
            let data = serverData.data;
            inst.helpList = data;
        });
    },
  }, 
  mounted(){
      
      this._loadData();
      
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
   
    .g-content-right{
        float: left;
        width: 958px;
        margin-left: 40px;
        
          line-height: 40px;

          margin-bottom: 40px;
    }
   
</style>
