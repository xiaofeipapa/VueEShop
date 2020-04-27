<template>
        
    <!-- 确认订单信息 -->
    <div class='c-order-box'>
        <div class="c-title">
            <div class="left">
                请确认订单信息
            </div>    
            <div class="right">
                <a href='/views/product/shopcart.html'>返回修改购物车</a>
            </div>    
        </div>    
        <div style="clear:both"></div>

        <!-- 表头 -->
        <div class="o-header">
            <li class="t1"><span>商品</span></li>
            <li class="t2">单价</li>
            <li class="t3">数量</li>
            <li class="t4">合计</li>
        </div>
        <div style="clear:both"></div>

         <!-- 商品1 -->
        <div class="order-item"
            v-for="item in productList"
            :key="item.pid"
            >
            <li class="c1">                        
                <div class="img-wrapper">
                        <img :src="item.productImage+'?x-oss-process=image/resize,m_fill,w_82,h_82'">
                    </div>
                    <div class="item-desc">
                            <nobr>{{item.modalTitle}}</nobr>
                    </div>
                    <ul class="attr-list">                                
                        <li>{{item.specValueString}}</li>
                    </ul>
            </li>
            <li class="c2">
                <div class="o-price">￥{{Number(item.marketPrice).toFixed(2)}}</div>
                <div class="now-price">￥{{Number(item.retailPrice).toFixed(2)}}</div>                        
            </li>
            <li class="c3">{{item.quantity}}</li>
            <li class="c4">￥{{Number(item.totalProductFee).toFixed(2)}}</li>
            <div style="clear:both"></div>
        </div>
    </div>
</template>

<script>

    import * as Ajax from '@/helper/Ajax';
    import * as VueHelper from '@/helper/VueHelper';
    import * as Urls from '@/helper/Urls';
    import store from '@/store'
    import { mapState, mapMutations, mapGetters } from 'vuex'
    
    import ModalConfirm from 'components/com/ModalConfirm';
    

export default {
  data() {
    return {
        shopItems: [], 

    }
  },
  store,
  components: {
      ModalConfirm, 

  },

  computed: {      
        ...mapGetters('shopStore', [
            'productList'
        ]),
  }, 
  
  watch: {

  }, 
  //相关操作事件
  methods: {

  },
  mounted(){
      
    
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    $border1:solid 1px #dddddd;

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


.order-item{
    font-size: 14px;
    border-top: solid 1px #eee;
    padding: 0 18px;
    .c1{
        width: 510px;

        .img-wrapper{
            float: left;
            margin-right: 20px;
            margin-top: 15px;
            margin-bottom: 15px;
            img{
                width: 82px;
                height: 82px;
            }
        }

        .item-desc{
            margin-top: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .attr-list{
            margin-top: 10px;
            font-size: 14px;
            color: #999;
        }
        li{
            float: left;
            padding: 1px 4px 1px 0px;
        }
    }
    .c2{
        width: 150px;
        margin-top: 30px;
        text-align: center;
        .o-price{
            text-decoration: line-through;
            color: #999;
        }
    }
    .c3{
        width: 150px;
        margin-top: 45px;
        text-align: center;
    }
    .c4{
        width: 150px;
        margin-top: 45px;
        text-align: center;
        color: $g-main-color;
    }
    .c5{
        width: 130px;
        margin-top: 45px;
        text-align: center;
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

