<template>
    <div id="app">
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <div class='g-content-wrapper'>

            <div class="w-content-box">
                <div class="w-content">
                    <p class="pp1"><i class="suc-pg"></i><span class="fs-18">订单已支付</span>
                    </p>
                </div>
            </div>

        </div>

            <BottomArticle />     
            <div style="clear:both"></div>

            <BottomLink />     
            <div style="clear:both"></div>
    </div>

</template>

<script>

    import * as Ajax from '@/helper/Ajax';
    import * as VueHelper from '@/helper/VueHelper';
    import * as Urls from '@/helper/Urls';
    import * as Events from '@/helper/Events';
    import store from '@/store'
    import { mapState, mapMutations, mapGetters } from 'vuex'
    
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';

    const ALI_PAY = 1;
    const WX = 2;
    const OFFLINE = 3;

export default {
  components: {
      TopUserSpan, BottomArticle, BottomLink, 

  },
  data() {
    return {

    }
  },
  store,
    computed: {


    }, 
  
  watch: {

  }, 
  methods: {
        
        
      
  },
  mounted(){

  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';
 $border1:solid 1px #dddddd;
    .color-grey{
        color: #aaa;
    }
    .color-red{
        color: red;
    }
    .w-content-box{
        width: 1200px;
        margin: 30px auto 40px auto;
        font-size: 16px;
        .fs-18{
            font-size: 24px;
        }
        .red-color{
            color: $g-main-color;
        }
        .pay-style{
            padding: 20px 0 40px;
            .spa{
                padding: 6px 26px;
                border: solid 1px #ddd;
                margin-right: 20px;
                display: inline-block;
                cursor: pointer;
                font-size: 12px;
                &:hover{
                    color: $g-main-color;
                }
            }
            > input[type=radio]{
                display: none;
                &:checked + .spa{
                    color: $g-main-color;
                    border: solid 1px $g-main-color;
                }
            }
            
        }
         .account{
            position: absolute;
            width: 278px;
            left: 0;
            color: #333;
            top: 38px;
        }
        
        .pp1{
            border-top: $border1;
            border-bottom: $border1;
            padding: 10px 40px;
            clear: both;
            .countDown{
                margin-left:20px;
                abbr{
                    color:red;
                }
            }
            .fs-18{
                vertical-align: middle;
            }
            .suc-pg{
                background-image: url(../../../assets/image/done_02.png);
                width: 20px;
                height: 20px;
                display: inline-block;
                vertical-align: middle;
                margin-right: 6px;
            }
        }
        .pp3{
            line-height: 48px;
            font-size: 18px;
            margin: 0 40px;
        }
        .pp2{
            margin: 0 40px;
        }
         @mixin text-overflow{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
         }
         .cashier{
            font-size: 18px;
            padding-left: 40px;
            margin-bottom: 10px;
         }
        .w-content{
            border: $border1;
        }
        .w-content-left{
            float: left;
            width: 100%;
            padding: 22px 40px;
            box-sizing: border-box;
            img{
                float: left;
                width:82px;
                height:82px;
                margin-right: 10px;
                border: solid 1px #eee;
            }
            ul{
                height: 94px;
            }
            li{
                margin-bottom: 10px;
                overflow: hidden;
                float: left;
                width: 50%;
            }
            .li-div{
                float: left;
                width: 465px;
            }
            .li-p1,.li-p2,.li-p3{
                @include text-overflow;
                line-height: 20px;
            }
            .li-p2,.li-p3{
                color:#999;
            }
            .li-p3{
                // margin-top: 24px;
            }
        }
        
    }
   


</style>
