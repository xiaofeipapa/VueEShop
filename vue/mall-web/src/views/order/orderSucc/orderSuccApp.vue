<template>
    <div id="app">
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <div class='g-content-wrapper'>

        <div class="w-content-box">
            <div class="w-content">
            <p class="pp1"><i class="suc-pg"></i><span class="fs-18">订单提交成功！</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号：<span class="red-color">{{bizId}}</span>
                <span class="countDown" v-if="mainStatus == '0'">
                    订单超时会自动关闭，请及时付款</span> <CountdownWidget ref="CountdownWidget" />
            </p>
            <p class="pp3">选择支付方式</p>
            <div class="pp2">
            <div class="pay-style">
                <input type="radio" value="1" id="wx" v-model="payStyle" name="payStyle"><label class="spa" for="wx">支付宝支付</label>
                <input type="radio" value="2" id="ali" v-model="payStyle" name="payStyle"><label class="spa" for="ali">微信支付</label>
                <input type="radio" value="3" id="offline" v-model="payStyle" name="payStyle"><label class="spa" for="offline">线下汇款</label>
                
                <div class="offline" v-show="isOffline">
                    温馨提示：<br>
                    1. 采用线下汇款方式到账会有延误，建议采用支付宝或微信实时到账。<br>
                    2. 线下汇款到账后会及时安排发货。各银行的到账时间一般为1-5天（具体到账时间以银行的实际到账时间为准）。 <br>
                    3. 汇款时请备注订单号, 方便我方更高效处理订单,安排发货. 
                    <br><br>
                    汇款账号信息如下：<br>
                    单位名称： {{config.offlineBankUser}}<br>
                    开户行： {{config.offlineBankFullName}}
                    <p>汇款账号：<span class="big">{{config.offlineBankAccount}}</span></p>
                    <p>应付金额<span class="big red"> ￥{{Number(totalShouldPaid).toFixed(2)}} </span></p>
                    <p>订单号<span class="big"> {{bizId}} </span></p>
                    
                </div>
            </div> 

            
            </div>
            

            
            </div>
            <div class="content-fr" v-show="!isOffline">
                <div class="p">应付金额:<span class="red-color">￥{{Number(totalShouldPaid).toFixed(2)}}</span></div>
                <div>
                    <LoadingButton ref="LoadingButton" @click="_doPay">去支付</LoadingButton>                    

                    <span class="error-msg">{{errorMsg}}</span>
                </div>
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
    import ModalConfirm from 'components/com/ModalConfirm';
    import CountdownWidget from 'components/com/CountdownWidget';
    import LoadingButton from 'components/com/LoadingButton';

    const ALI_PAY = 1;
    const WX = 2;
    const OFFLINE = 3;

export default {
  components: {
      TopUserSpan, BottomArticle, BottomLink, ModalConfirm, CountdownWidget, LoadingButton

  },
  data() {
    return {

        bizId: '', 
        
        totalShouldPaid:0,                //应付金额
        payStyle: '',                    //支付方式
        errorMsg:"",

        config: {}, 

        mainStatus:0,//订单状态
    }
  },
  store,
    computed: {

        isOffline(){
            return this.payStyle == OFFLINE;
        }, 

    }, 
  
  watch: {

  }, 
  methods: {
        
        _doPay(){

            let btn = this.$refs.LoadingButton;

            if(!this.payStyle){
                this.errorMsg = '请选择支付方式';
                btn.closeLoading();
                return;
            }

            // 微信支付宝需要配置, 现在先直接走完成. 

            let inst = this;
            let url = Urls.userOrder_payIt;
            let params = {
                'oid': this.bizId, 
                'payStyle': this.payStyle, 
            }

            Ajax.post(url, params, function(serverData){
                
                btn.closeLoading();

                // 登录超时
                if(serverData.error && serverData.errorCode == 'CODE_SESSION_EXPIRE'){
                    inst.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);
                    return;
                }

                // 正常错误
                if(serverData.error){
                    inst.errorMsg = serverData.error;
                    return;
                }
                
                window.location.href = '/views/order/paid.html';
            });
        }, 

        _doInit(){

            // 解析页面参数
            let queryString = VueHelper.parseUrlQueryString();
            // console.log('--- queryString: ' + queryString);
            let oid = queryString.split('=')[1];

            let inst = this;
            let url = Urls.userOrder_getCountdown;
            let params = {
                'oid': oid, 
            }

            Ajax.post(url, params, function(serverData){

                // 正常错误
                if(serverData.error){
                    inst.errorMsg = serverData.error;
                    return;
                }

                // 成功之后跳转
                let vo = serverData.data;

                inst.bizId = vo.bizId;
                inst.totalShouldPaid = vo.totalShouldPaid;
                inst.config = vo.config;

                inst.$root.Bus.$emit(Events.CHILD_INIT, vo);
            });
        }, 

      _initAndMustLogin(){
          
        let inst = this;
        let callback = function(){
            inst._doInit();
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
            color: $g-red;
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
    .content-fr{
        font-size: 18px;
        margin-left: 40px;
        margin-bottom: 40px;
        .p{
            line-height: 58px;
        }
        .error-msg{
            color: red;
            font-size: 16px;
            white-space: nowrap;
            margin-left: 20px;
        }
    }
    /** 使用余额 **/
        .deposite-box{
            border-top: 1px dashed #ddd;
            border-bottom: 1px dashed #ddd;
            padding-bottom: 25px;
            margin-bottom: 20px;
            .close-deposit{
                height: 50px;
                line-height: 50px;
                cursor: pointer;
                i{
                    color: $g-main-color;
                    margin-right: 10px;
                }
                .red{
                    color: $g-main-color;
                }
            }

            .expand-deposit{
                height: 50px;
                line-height: 50px;
                cursor: pointer;
                i{
                    color: $g-main-color;
                    margin-right: 10px;
                }
            }
            .d-amount{
                .red{
                    color: $g-main-color;
                }
            }
            .d-btn-group{
                margin-top: 15px;
                position: relative;

                .rmb-img{
                    position: absolute;
                    left: 10px;
                    top: 7px;
                }

                .amount-value{
                    padding: 10px 10px 10px 30px;
                    outline: none;
                    border: $g-box-border;
                    font-size: 16px;
                    width: 120px;
                    margin-right: 20px;
                }
                .d-btn{
                    margin-right: 20px;
                }
            }

        }

.offline,.shouxin{
    background-color: #eee;
    margin: 33px 0;
    padding: 10px;
    .big{
        color: $g-main-color;
        font-size: 18px;

        &.red {
            color: $g-red;
        }
    }
    .inp{
        vertical-align: top;
        width: 600px;
        resize: none;
        height: 100px;
        padding: 6px;
        border-color: #ccc;
    }
    p{
        margin-top: 5px;
        margin-bottom: 10px;
    }
    .mr{
        margin-right: 20px;
    }
    .mr2{
        margin-right: 50px;
    }
    .wds{
        display: inline-block;
        text-align: center;
        color: #888;
    }
    .w1{
        width: 200px;
    }
    .w2{
        width: 236px;
    }
    .w3{
        width: 222px;
    }
   
    .sx-tip{
        border-top: solid 1px #999;
        padding-top: 10px;
        
    }
}

</style>
