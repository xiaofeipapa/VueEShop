<template>
  <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
  <div class="header-toolbar">
    <div class="left-items">
      <span @click="_goHome">商城首页</span>
    </div>
    <div class="toolbar-items">
      <template v-if="isLogin">
        <a class="item-menu">
           <span class="item-text red" @click="_goUserIndex">{{userInfo.account}}</span>

            <a class="item-menu" @click="_goLogout">退出登录</a>
        </a>
      </template>
      <template v-else>
        <span class="item-text red" @click="_goLogin">你好,请登录</span>
        <a class="item-menu" @click="_goRegister">免费注册</a>
      </template>
      <a class="item-menu" @click="_goHelp">
        帮助中心
        <i class="fa fa-angle-down"></i>
      </a>
      <!-- <a class="item-menu">
        网站导航
        <i class="fa fa-angle-down"></i>
      </a> -->
    </div>
  </div>
</template>

<script>
// =============== 最顶部的用户区域, 如 免费注册 / 帮助中心, 热线电话
/**
 * ----------------------------------- 头部登录信息的设计: 
 * 
 * 和jsp不同, vue和server 是分离的, 也就是没办法保持登录状态的联系. 能做的办法是: 
 * 1) 定时到后面获得状态
 * 2) 设置一个假的状态, 在实际要到登录后的页面的时候再检查登录. 
 * 
 * 现在采取第二种做法. 
 */
    import * as Urls from '@/helper/Urls';
    import * as Ajax from '@/helper/Ajax';
    
    import { mapState, mapMutations, mapGetters } from 'vuex'
    

export default {
  name: "TopUserSpan",

  data() {
    return {
      
    };
  },
  components: {},
    computed:{
        ...mapGetters('userStore', [
                'userInfo', 'isLogin'
            ]),
    }, 

  methods: {

      ...mapMutations('userStore', [
            'clearUser'
        ]),

      _goUserIndex(){
          window.location.href = "/views/user/index.html";
      }, 

      _goHelp(){
          window.location.href = "/views/common/help.html";
      }, 

      _goLogout(){

          let url = Urls.user_logout;
          let params = {
          };

          let inst = this;
          Ajax.post(url, params, function (server_data){              
              inst.clearUser();
              window.location.reload();
          });
      }, 
      _goHome() {
          window.location.href = "/views/home/index.html";
      },

      _goLogin() {
          window.location.href = "/views/user/login.html";
      },
      _goRegister() {
          window.location.href = "/views/user/register.html";
      }
  }, 
  mounted() {

  }
};
</script>

<style lang="scss" scoped>
    @import '../../assets/scss/themeDefault.scss';
.red {
  color: $g-main-color;
}
.header-toolbar {
  width: 1250px;
  margin: 0 auto;
  height: 30px;
  line-height: 30px;

    .left-items{
      float: left;
      color: #9c9c9c;
      font-size: 13px;
      cursor: pointer;
    }
}
.header-toolbar .toolbar-items {
  float: right;
  margin-right: 20px;
  color: #9c9c9c;
  font-size: 13px;
}
.header-toolbar .toolbar-items .item-text {
  cursor: pointer;
  padding: 4px 5px 4px 5px;
}
.header-toolbar .toolbar-items .item-menu {
  padding: 4px 5px 4px 5px;
  cursor: pointer;
}
.header-toolbar .toolbar-items .item-menu i {
  margin-left: 5px;
}
</style>
