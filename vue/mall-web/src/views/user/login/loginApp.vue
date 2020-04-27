<template>
<div class="max-width-wrapper">

    <TopAdSpan />
    <div style="clear:both"></div>

    <div class='g-box-wrapper'>
    
        <div class="left-items">
        <span @click="_goHome">商城首页</span>
        </div>
	    <div class='r-content-box'>

            <div class="c-1">                
                <img src='../../../assets/image/login_ad.jpg'>
            </div>
            <div class="c-2">   
                <LoginWidget ref="LoginWidget" @loginSucc="_doAfterLogin"/>
            </div>

        </div>
    </div>
    

    <BottomArticle />
    <div style="clear:both"></div>

    <BottomLink />
    <div style="clear:both"></div>
    <LoadingScreen
        ref='loadingScreen'/>

</div>
</template>

<script>

    import Vue from 'vue'
    import TopAdSpan from '@/components/biz/TopAdSpan';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import LoadingScreen from 'components/com/LoadingScreen';
    import LoginWidget from 'components/biz/LoginWidget';
    import GetIdentifyCode from 'components/biz/GetIdentifyCode';
    import * as Urls from '@/helper/Urls';
    import * as Ajax from '@/helper/Ajax';
    

export default {
  data() {
    return {
        
    }
  },
  components: {
        TopAdSpan , BottomArticle, LoadingScreen, GetIdentifyCode
        , BottomLink, LoginWidget
      
  },
  watch:{


  },
  computed:{
            
      
  },
  //相关操作事件
  methods: {
      _doAfterLogin(){
            // console.log('--- login succ');
            window.location.href = '/views/home/index.html';
      },       
      _goHome() {
          window.location.href = "/views/home/index.html";
      },
      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .left-items{
      color: #9c9c9c;
      font-size: 13px;
      cursor: pointer;
      margin-top: 10px;
      margin-left: 100px;
    }
    
    .g-header-toolbar-wrapper{
        width: 100%;
        background: rgb(250, 250, 250);    
        height: 30px;
        line-height: 30px;

        .g-header-toolbar{
            width: $g-main-width;
            margin: 0 auto;

            .address-selector{
                float:left;
                width:100px;
                .address-text{
                    color: rgb(156, 156, 156);
                    font-size: 13px;        
                }
            }
            .toolbar-items{
                float: right;
                margin-right: 20px;
                color: rgb(156, 156, 156);
                font-size: 13px;   

                .item-text{
                    padding: 4px 5px 4px 5px;   
                }

                .item-menu{
                    padding: 4px 5px 4px 5px;   
                    cursor: pointer;

                    i{
                        margin-left: 5px;
                    }
                }
                .item-menu.red{
                    color: $g-main-color;
                }
            }

        }
    }

    /** 注册表格 **/
    .g-box-wrapper{
        width: 100%;    
        background: #fff;
        .r-content-box{
            @include g-page-content-row;      
            
            .sep-150{
                width: 100%;
                height: 150px;
            }
        }

        .grey-text{
            color: #999;
        }

        .r-label{
            .req{
                color: $g-main-color;
                margin-right: 5px;
            }
            position: absolute;
            margin-left: 70px;
        }
    }



/** 登录页面相关 **/
.r-content-box{
    .c-1{
        float: left;
        img{
            display: block;
            width: 528px !important;
            height: 463px;
        }
    }
    .c-2{
        padding-top: 50px;
        float: left;
        margin-left: 100px;
        margin-top: 26px;
    }
}

   
</style>
