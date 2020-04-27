<template>
    <div id="app">
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <div class='g-content-wrapper'>        
            <p class="wrapper-title">填写并核对订单信息</p>
            <div style="clear:both"></div>
            
            <!-- ================ 收货人信息 begin -->
            <DeliveryInfo ref="DeliveryInfo"
                @selectAddress="_getFullAddress"
                />
            <div style="clear:both"></div>
            <!-- ================ 收货人信息 end -->
            
            <!-- ================ 订单信息 begin -->
            <OrderInfoList ref="OrderInfoList"/>
            <div style="clear:both"></div>
            <!-- ================ 订单信息 end -->

            <!-- 其他 -->
            <div class='c-order-box'>
                <div class="c-title">
                    <div class="left">
                        其他
                    </div>      
                </div>    

                <!-- 留言 -->
                <div class="order-leave-msg">
                    <span class="label">给卖家留言:</span>
                    <textarea class="leave-msg" placeholder="选填（填写与卖家协商确认的内容）" v-model="orderRemark"></textarea>
                </div>

            </div>
            <div style="clear:both"></div>
            
            <!-- 合计信息区 -->
            <div class="total-box">
                <div class="row">
                    <div class="c1">商品总金额:</div>
                    <div class="c2">￥<span>{{totalProductFee}}</span></div>
                </div>       
                <div class="row">
                    <div class="c1"><span class="d-fee">（{{feeResult.msg}}）</span>运费:</div>
                    <div class="c2">￥<span>{{Number(feeResult.fee).toFixed(2)}}</span></div>
                </div>  
                <div class="row">
                    <div class="c1">订单总金额:</div>
                    <div class="c2">￥<span>{{_getOrderTotal()}}</span></div>
                </div>                  
            </div>

            <!-- 最终信息区 -->
            <div class="final-box">
                <div class="row">
                    <span class="f-label-grey">寄送至: <span>{{fullAddress}}</span></span>    
                    <span class="f-label">应付总额:</span><span class="money">￥</span>
                    <span class="money">{{_getOrderTotal()}}</span>
                    
                </div>    
            </div>

            <!-- 按钮 -->
            <div class="final-btn-box"> 
                <div class="row" style="margin-bottom:10px;" v-if="msgList && msgList.length > 0">        
                    <span class='error-text' v-for="msg in msgList" :key="'msg-' + msg">{{msg}}</span>    
                </div>      
                <div class="row">       
                    <LoadingButton 
                        @click="_createNewOrder"
                        ref="LoadingButton">提交订单</LoadingButton>
                </div>      
            </div>
            <div style="clear:both"></div>

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
    import LoadingButton from 'components/com/LoadingButton';

    import DeliveryInfo from './DeliveryInfo';
    import OrderInfoList from './OrderInfoList';
        
    
export default {
  data() {
    return {
        
        feeResult: {
            'msg': '', 
            'fee': 100, 
        },       // 快递计算

        fullAddress: '', 
        orderRemark: '', 
        msgList: [], 
    }
  },
  store,
  components: {
      TopUserSpan, BottomArticle, BottomLink, DeliveryInfo, LoadingButton, 
      OrderInfoList, ModalConfirm

  },
    computed: {

        ...mapGetters('shopStore', [
            'totalProductFee', 'productList'
        ]),
    }, 
  
  watch: {

  }, 
  methods: {
      ...mapMutations('shopStore', [
          'clearProducts'
      ]),

        // 未来可能需要加入快递计算逻辑

        _getOrderTotal(){
            let t = Number(this.totalProductFee) + Number(this.feeResult.fee);
            // console.log('--- this.totalProductFee: ', this.totalProductFee);
            // console.log('--- feeResult.fee: ', this.feeResult.fee);
            // console.log('--- total: ', t);
            t = t.toFixed(2);
            return t;
        }, 
        _getFullAddress(data){
            let u = data.name;
            let p = data.phone;
            this.fullAddress = u + '  ' + p + '  ' + VueHelper.popFullAddress(data);
        }, 

        _createNewOrder(){

            let btn = this.$refs.LoadingButton;

            // 获取商品
            let prods = [];
            this.productList.forEach(prod => {
                let d = {
                    'id': prod.pid, 
                    'count': prod.quantity, 
                }
                prods.push(d);
            });

            let inst = this;

            let url = Urls.userOrder_makeOrder;
            let params = {
                'products': prods, 
                'totalProductFee': this.totalProductFee, 
                'deliveryFee': this.feeResult.fee, 
                'totalShouldPaid': this._getOrderTotal(), 
                'goodAddressId': this.$refs.DeliveryInfo.getData().id, 
                'orderRemark': this.orderRemark, 
            }

            params = {
                'jsonData': JSON.stringify(params), 
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
                    if(serverData.data){
                        inst.msgList = serverData.data.errors;
                    }else{
                        inst.msgList = [serverData.error];
                    }
                    return;
                }

                // 成功之后清空购物车
                inst.clearProducts();

                let bizId = serverData.data;
                window.location.href = '/views/order/orderSucc.html?oid=' + bizId;
            });
        }, 

        _doInit(){
            let inst = this;
            inst.$nextTick(_ => {
                inst.$refs.DeliveryInfo.doInit();
            });
        }, 
        
      
  },
  mounted(){

      // 此页面需要登录保护
      this.$root.Bus.$emit(Events.NEED_MODAL_LOGIN);

    // 如果没有登录, 在登录成功之后重新初始化页面
    let event = Events.LOGIN_SUCC;
    this.$root.Bus.$off(event).$on(event, this._doInit);
       
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    #app{
        width: 100%;
    }
    $border1:solid 1px #dddddd;
    .color-grey{
        color: #999;
    }
    .color-red{
        color: $g-main-color;
    }
    .baseline{
        vertical-align: middle;
    }
    .cursor{
        cursor: pointer;
    }
    
.g-content-wrapper{
    @include g-page-content-row;
    .wrapper-title{
        font-size: 18px;
        margin-top: 40px;
    }

    .left{
        float: left;
        font-size: 18px;
    }
    li{
        float: left;
        list-style: none;
    }
    .label{
        color: #000;
        font-size: 15px;
    }
    .check-title-big{
        margin-top: 20px;
        margin-bottom: 10px;
    }
    .sep{
        border-bottom: $border1;
    }
}


    .left{
        float: left;
        font-size: 18px;
    }
    .right{
        float: right;
        padding-right: 20px;
        a{
            cursor: pointer;
            font-size: 14px;
        }
    }

    li{
        float: left;
        list-style: none;
    }
    .label{
        color: #000;
        font-size: 15px;
    }
    .check-title-big{
        margin-top: 20px;
        margin-bottom: 10px;
    }
    .sep{
        border-bottom: $border1;
    }

    /** 订单信息 **/
    .c-order-box{
        padding: 0 36px;
        border: solid 1px #eee;
        padding-bottom: 40px;
        .c-title{
            height: 50px;
            line-height: 50px;
            border-bottom: $border1;
            .left{
                float: left;
                font-size: 16px;
                font-weight: bold;
            }
            .right{
                float: right;
                padding-right: 20px;
                a{
                    cursor: pointer;
                    font-size: 14px;
                }
            }
        }
        .o-header{
            height: 50px;
            line-height: 50px;
            font-size: 14px;
            padding: 0 18px;
            .t1{
                width: 510px;
                text-align: center;
                
            }
            .t2{
                width: 150px;
                text-align: center;
            }
            .t3{
                width: 150px;
                text-align: center;
            }
            .t4{
                width: 150px;
                text-align: center;
            }
            .t5{
                width: 130px;
                text-align: center;
            }
        }
    }



    .total-box{
        margin-top: 30px;
        padding-right: 40px;
        font-size: 16px;
        .row{
            height: 30px;
            line-height: 30px;
            display: flex; 
            flex-flow: row;
            justify-content: flex-end;

            .c2{
                text-align: right;
                margin-left: 8px;
            }
        }
        .d-fee{
            font-size: 12px;
            margin-right: 5px;
            color: #999;
        }

    }
     .final-box{
        padding-top: 20px;
        padding-right: 20px;
        background: #f8f8f8;
        border: 1px solid #eaeaea;
        padding-bottom: 20px;
        font-size: 14px;
        .row{
            height: 30px;
            line-height: 30px;
            text-align: right;
            .f-label{
                margin-right: 10px;
            }
            .d-btn{
                margin-top: 5px;
                height: 36px;
                line-height: 36px;
                font-size: 16px;
            }

            .money{
                color: $g-red;
                font-size: 32px;
            }

        }

    }

    .f-label-grey{
        margin-right: 20px;
    }

    .final-btn-box{
        margin-top: 20px;
        padding-right: 20px;
        margin-bottom: 20px;
        .row{
                text-align: right;
            // height: 30px;
            // line-height: 30px;
            // display: flex; 
            // flex-flow: row;
            // justify-content: flex-end;

            .submit-btn{
                padding-top:10px;
                padding-bottom:10px;
                padding-left: 30px;
                padding-right: 30px;
                height: 36px;
                line-height: 36px;
                font-size: 24px;
                color: #fff;
                background: $g-main-color;
                cursor: pointer;
                display: inline-block;
            }

            .submit-btn.invalid{                
                background: #999;
                border-color: #ccc;
                cursor: not-allowed;
            }
        }
        .error-text{            
            color: $g-red;
            font-size: 18px;
        }
    }

.order-leave-msg{
    padding-left: 94px;
    position: relative;
    margin-top: 40px;
    span{
        width: 94px;
        vertical-align: top;
        position: absolute;
        left: 0;
        font-size: 14px;
    }
    .leave-msg{
        width: 100%;
        resize: none;
        height: 80px;
        padding: 8px;
        box-sizing: border-box;
        border: $border1;
    }
}

</style>
