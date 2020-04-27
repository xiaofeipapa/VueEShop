<template>
    <div id="app">
        
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <div class='g-content-wrapper'>

        <div class='g-content-box'>
            <div class="no-data" v-if="!invalidItems.length && !validItems.length">
                <div class="is-loading" v-if="isLoading"><img src='../../../assets/image/loading.gif' /> 正在加载,请稍候......</div>
                <template v-else>
                    <i class="car-pg"></i>
                    <span>
                    您的购物车空空的哦~去看看心仪的商品吧!<br>
                    <a href="/views/home/index.html">去购物>></a>
                    </span>
                </template>
            </div>
            <template  v-else>
	        <!-- 表格头 -->
            <ul class='shop-header'>
                    <li class="c1">                        
                        <span class="check-item">
                            <input type='checkbox' id='check-filter-all'
                                ref='selectAllBox'
                                @click="selectAll($event)"
                            >
                            <label for='check-filter-all'></label>全选
                        </span>
                    </li>
                    <li class="c2">商品信息</li>
                    <li class="c3">单价</li>
                    <li class="c4">数量</li>
                    <li class="c5">小计</li>
                    <li class="c6">操作</li>
            </ul>
            <div class='clear-both'></div>
            
	        <!-- 注意: 所有的商品都包含在batch-item 类里 -->
            <!-- 已选择商品 -->
            <div class='batch-item'>
                <ul class='ok-item'
                    v-for="item in validItems"
                    :key="item.pid"
                >
                    <li class="c1">         
                        <div class="invalid-text">                                                                                       
                                <span class="check-item">
                                    <input type='checkbox' :id="'check-filter-' + item.pid"
                                    v-model="item.selected"
                                    @click="selectOne($event,item)"
                                    >
                                    <label :for="'check-filter-' + item.pid"></label>
                                </span>
                        </div>
                        <div class="img-wrapper">
                            <a :href="'/views/product/detail.html?brandId='+item.brandId+'&pid='+item.pid">
                                <img :src="item.productImage+'?x-oss-process=image/resize,m_fill,w_90,h_90'">
                            </a>
                        </div>
                    </li>
                    <li class="c2">
                        <div class="item-desc">
                            <a :href="'/views/product/detail.html?brandId='+item.brandId+'&pid='+item.pid"><nobr>{{item.label}}</nobr></a>
                        </div>

                        <div class="item-title"            
                            >
                            {{item.modalTitle}}
                        </div>

                        <div class="attr-list"       
                            >
                            {{item.specValueString}}
                        </div>
                        <div class='clear-both'></div>
                    </li>
                    <li class="c3">
                        <div class="o-price">￥{{Number(item.marketPrice).toFixed(2)}}</div>
                        <div class="now-price">￥{{Number(item.retailPrice).toFixed(2)}}</div>
                    </li>
                    <li class="c4">
                        <ul class="g-qutity-box">
                            <li
                                @click="minusQuantity($event, item)"
                            ><div class="btn">-</div></li>
                            <li>
                                <div  class="qutext">
                                    <template>
                                        <input type='text'
                                    v-model="item.quantity"                                    
                                    @input="changeQuantity($event, item)"
                                    >
                                    </template>
                                </div>
                            </li>
                            <li
                                @click="addQuantity($event, item)"
                            ><div class="btn right">+</div></li>
                        </ul>
                        <div class="stock"><p>库存  {{_remainStock(item)}}</p></div>
                        <transition name="fade">
                            <div class="stockMax">
                                <p v-if="_isMaxBuy(item)">最多只能购买{{_maxBuy(item)}}件</p>
                                <!-- <p v-else-if="item.quantity > (item.saleStock - 10)">库存紧张</p>
                                <p v-else></p> -->
                            </div>
                        </transition>
                        
                        
                    </li>
                    <li class="c5 red">￥{{Number(item.totalProductFee).toFixed(2)}}</li>
                    <li class="c6"><a @click="_showDeleteModal(item.pid)">删除</a><br><br><a @click="followGoods(item.pid)">关注</a></li>
                </ul>
            </div>
	        <!-- 无效商品 -->
            <div class='batch-item' v-if="invalidItems.length">
                <ul class='invalid-item'
                    v-for="item in invalidItems"
                    :key="item.pid"
                    >
                    <li class="c1">         
                        <div class="invalid-text">失效</div>
                        <div class="img-wrapper">
                            <!-- <a :href="'/views/product/detail.html?brandId='+item.brandId+'&pid='+item.pid"> -->
                            <img :src="item.productImage+'?x-oss-process=image/resize,m_fill,w_90,h_90'">
                            <!-- </a> -->
                        </div>
                    </li>
                    <li class="c2">
                        <div class="item-desc">
                            <!-- <a :href="'/views/product/detail.html?brandId='+item.brandId+'&pid='+item.pid"> -->
                            <nobr>{{item.label}}</nobr>
                            <!-- </a> -->
                        </div>
                        <div class="attr-list">
                            {{item.specValueString}}
                        </div>
                        <div class='clear-both'></div>
                    </li>
                    <li class="c3">
                            <div class="o-price">￥{{Number(item.marketPrice).toFixed(2)}}</div>
                            <div class="now-price">￥{{Number(item.retailPrice).toFixed(2)}}</div>
                    </li>
                    <li class="c4">{{item.quantity}}</li>
                    <li class="c5">￥{{Number(item.totalProductFee).toFixed(2)}}</li>
                    <li class="c6 remove" @click="_showDeleteModal(item.pid)">删除</li>
                </ul>
            </div>

            

                <div class='clear-both'></div>
                <!-- 底部结算框 -->
                <div class="shop-bottom-bar">
                    <div class="left">
                        <span class="check-item">
                            <input type='checkbox' id='check-filter-all-2'
                                ref='selectAllBox2'
                                @click="selectAll($event)"
                            >
                            <label for='check-filter-all-2'></label>全选
                        </span>
                        <span class="mar" @click="_showDeleteModal('select')">删除选中的商品</span>
                        <span class="mar" @click="followGoods('select')">移到我的关注</span>
                    </div>
                    <div class="right">   
                        <LoadingButton ref="orderBtn"  @click="_makeOrder()">去结算</LoadingButton>
                    </div>
                    <div class="middle">   
                        <span class="error-text">{{errorMsg}}</span>
                        <span>已选择</span>
                        <span class="red">{{allQuantity}}</span>
                        <span>件商品&nbsp;&nbsp;&nbsp;&nbsp;合计(不含运费)：</span>
                        <span class="red">￥{{Number(allPrice).toFixed(2)}}</span>
                    </div>
                </div>
            </template>
                <div class='clear-both'></div>
        </div>

    </div>

      <ModalConfirm ref="deleteModel" title="删除" okText="删除" cancelTxt="移到我的关注"
          :width="360" :height="166"
          @ok="_removeGoods"
          @cancel = "moveToFollow"
      >
          <div slot="content">
              <div class="dm-txt">
                  <p class="p1">删除商品？</p>
                  <p class="p2">您可以选择移到关注，或删除商品。</p>
              </div>
          </div>
      </ModalConfirm>

    <LoadingScreen
        ref='loadingScreen'/>

      <BottomArticle />
      <BottomLink />
        
    </div>
</template>

<script>

    import * as Ajax from '@/helper/Ajax';
    import * as VueHelper from '@/helper/VueHelper';
    import * as Urls from '@/helper/Urls';
    import * as Events from '@/helper/Events';
    
    import * as CacheHelper from '@/helper/CacheHelper';
    import store from '@/store'
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import HorizontalAd from '@/components/biz/HorizontalAd';
    import ModalMsg from 'components/com/ModalMsg';
    import ModalConfirm from 'components/com/ModalConfirm';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import LoadingButton from '@/components/com/LoadingButton';
    import LoadingScreen from 'components/com/LoadingScreen';
    import Vue from 'vue'

    const max_buy = CacheHelper.getMaxBuy();
    

export default {
    data() {
      return {
        
          isLoading:false,
          invalidItems: [],       // 无效的商品信息. 在购物车太久, 该商品有可能已经下架
          validItems:[],          // 有效的商品信息
          allQuantity: 0,            // 总数
          allPrice: 0,            // 总价格(不含运费)
          currentItem: '',    // 正在改变属性的shop item
          errorMsg: '',      // 改变过程中的错误
          currentPids: [],    // 选中的商品id数组

      }
    },
    store,
    components: {
      TopUserSpan, HorizontalAd, ModalMsg, ModalConfirm, BottomLink, 
        BottomArticle, LoadingButton, LoadingScreen
    },
  
    computed: {

        ...mapGetters('shopStore', [
            'productIds', 'productList'
        ]),

        theProducts() {
              return this.validItems;
          }

    }, 

    watch:{
        validItems:{
              handler:function(newArr,oldArr){
                  // console.log('-- newArr', newArr);
                  // console.log('-- oldArr', oldArr);
                  let total = 0;
                  let allq = 0;
                  newArr.forEach(element => {

                      element.totalProductFee =  element.quantity * parseFloat(element.retailPrice);
                      if(element.selected){
                          total += element.totalProductFee;
                          allq += 1;
                      }
                  });
                  this.allPrice = total;
                  this.allQuantity = allq;
                  // console.log('--- allPrice: ', this.allPrice);
              },
              deep:true, //对象内部的属性监听，也叫深度监听
          }, 

    }, 
  //相关操作事件
  methods: {

      ...mapMutations('shopStore', [
          'deleteProduct', 'updateProducts'
      ]),

      _maxBuy(item){
          if(!item.saleStock){
              item.saleStock = max_buy;
          }

          let q = max_buy <= item.saleStock ? max_buy : item.saleStock;
          return q;

      }, 

      _remainStock(item){
          return item.saleStock - item.quantity;
      }, 

      _isMaxBuy(item){
          let q = this._maxBuy(item);
          return item.quantity >= q;
      }, 

      // 最大允许购买数量
      _checkMaxBuy(item){

          let q = this._maxBuy(item);
          if(item.quantity > q){
              item.quantity = q;
          }
      }, 

      _makeOrder: function(){

        let btn = this.$refs.orderBtn;
        
        if(this.allQuantity < 1){
            this.errorMsg = '请选择商品';
            btn.closeLoading();
            return;
        }

        this.errorMsg = '';

        this.$refs.loadingScreen.show();

        // 用于测试
        // setTimeout(() => {
        //     inst.$refs.loadingScreen.hide();
        //     btn.closeLoading();

        // }, 3000);

        // 更新store
        this.updateProducts(this.validItems);

        let inst = this;

        // 到订单确认页
        window.location.href = '/views/order/confirm.html';

    },

        // 选择所有商品
      selectAll : function(event){
            // console.log(event.target);
            var el = event.target;
            let setValue = el.checked;
            this.$refs.selectAllBox.checked = setValue;
            this.$refs.selectAllBox2.checked = setValue;
            this.validItems.forEach(theItem => {
                theItem.selected = setValue;
            });

            // this._calAll();
        }, 


      // 选择单个商品
      selectOne : function(event, item){

          item.selected = !item.selected;

          // this._calAll();

          // 改变样式
          let oneSelect = false;      // 是否有一项没选
          this.validItems.forEach(theItem => {
              if(!theItem.selected){
                  oneSelect = true;
              }
          });

          // console.log('----: ' + this.$refs.selectAllBox);
          if(oneSelect){
              this.$refs.selectAllBox.checked = false;
              this.$refs.selectAllBox2.checked = false;
          }else{
              this.$refs.selectAllBox.checked = true;
              this.$refs.selectAllBox2.checked = true;
          }
      }, 

        // 增加数量
      addQuantity(event, item){

          item.quantity += 1;

          this._checkMaxBuy(item);            
            
        }, 
        // 减少数量
      minusQuantity(event, item){     
            if(item.quantity == 1)return;

            item.quantity = parseInt(item.quantity) - 1;

            // console.log('--- item: ', item);
            
        }, 

        // 防止用户不输入
        changeQuantity(event, item){
            item.quantity = item.quantity.replace(/\D/g, '');
            if( ! item.quantity || item.quantity < 1){
                item.quantity = 1;
            }
            
            this._checkMaxBuy(item);        
        }, 

      // 初始化购物车
      _initShopcart(){
          // console.log('--- productIds: ', this.productIds);

          // 购物车缓存没有商品
          if( !this.productIds || this.productIds.length < 1){
              return;
          }

          // 检查服务器数据, 防止商品已经失效
          let url = Urls.shopcart_checkProducts;
          let params = {
              'jsonData': JSON.stringify({
                  'pids': this.productIds, 
              })
          }

          this.validItems = [];
          this.invalidItems = [];

          let inst = this;
          Ajax.post(url, params, function(serverData){
              
              let voList = serverData.data;

              //console.log('--- voList: ', voList);
              // 复制缓存的商品到本地变量(因为vuex的数据不能直接用作v-model)
              
              // 商品的结构
              /**
               * {
               *  'pid': 
               *  'productImage': 
               *  'specValueString':
               *  'marketPrice':
               *  'retailPrice':
               *  'saleStock'
               *  'quantity', 
               *  'selected', 
               * 
               * }
               */

              voList.forEach(vo => {
                
                  inst.productList.forEach(shopProd => {

                      // 附上数量
                      if(shopProd.pid == vo.id){
                          vo.pid = vo.id;
                          vo.quantity = shopProd.quantity;
                          vo.selected = false;
                          vo.totalProductFee = vo.quantity * vo.retailPrice;
                      }
                  });

                  if(vo.dataStatus == 'onSale'){
                      inst.validItems.push(vo);
                  }
                  else{
                      inst.invalidItems.push(vo);
                  }

              });

          });


      }, 
      _showDeleteModal(id){
        
        let pids = [];
        if(id == "select"){
            this.validItems.forEach(element => {
                if(element.selected){
                    pids.push(element.pid);
                }
            });
            
            if( pids.length < 1){
                this.$refs.msgModel.show("请选择商品");
                return ;
            }
        }else{
            pids.push(id);
        }

        this.currentPids = pids;
        
        this.$refs.deleteModel.show();
    },
    //删除商品
    _removeGoods(){
        let inst = this;

        // 删除vuex里的数据
        this.deleteProduct(this.currentPids);
        this.currentPids = [];
    
        // 重置商品
        this._initShopcart();
    },
    moveToFollow(){
        this._removeGoods();
        this.followGoods();
    },
    //关注商品
    followGoods(productId){
        let inst = this;
        if(productId == "select"){//移到我的关注，关注并从购物车删除
            productId = "";
            for(var i=0; i<inst.validItems.length; i++){
                var item = inst.validItems[i];
                if(item.selected){
                    productId += item.productId + ',';
                }
            }
            if(!productId){
                this.msg = "请选择商品～";
                this.$refs.msgModel.show();
                return false;
            }

        }
        // 加载数据
        // Lib.M.ajax({
        //     'url': Urls.product_follow,
        //     'data':{
        //         productId:productId || inst.currentProductsId,
        //     },
        //     'success':function (server_data){
        //         if(server_data.sessionExpire == 'Y'){
        //             window.location.href="/views/user/login.html";
        //             return;
        //         }
                
        //         if(server_data.returnCode == '0'){
        //             inst.msg = "已关注";
        //             inst.$refs.msgModel.show();
        //         }else{
                    
        //             inst.msg = server_data.returnMsg;
        //             inst.$refs.msgModel.show();
        //         }

        //     }
        // });
    },

    _initAndMustLogin(){
          
        let inst = this;
        let callback = function(){
            inst._initShopcart();
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

.g-content-wrapper{
    width: 100%;
    margin-top: 40px;
    .g-content-box{
        @include g-page-content-row;
    }
}

.g-content-box{

    .attr-change-box{
        cursor: pointer;
    }

    /** 统一列样式 **/
    li{
        float: left;
    }

    .c1{
        padding-left: 20px;
        width: 160px;
    }
    .c2{
        padding-left: 20px;
        width: 420px;
    }
    .c3{
        padding-left: 10px;
        width: 100px;
        text-align: center;
    }
    .c4{
        padding-left: 10px;
        width: 160px;
        text-align: center;
    }
    .c5{
        padding-left: 10px;
        width: 120px;
        text-align: center;
    }
    .c6{
        padding-left: 10px;
        width: 120px;
        text-align: center;
    }

    /** 表格头 **/
    .shop-header{
        height: 60px;
        line-height: 60px;
        background: #f8f8f8;
        border: 1px solid #cccccc;
        font-size: 16px;
    }

    .batch-item{
        margin-top: 20px;
        border: $g-box-border;
        border-bottom:0 none;
    }

    /** 表格内容row 无效 **/
    .invalid-item{
        height: 120px;
        background: #eee;
        color: #a3a3a3;
        border-bottom:$g-box-border;
        .invalid-text{
            padding: 1px 2px;
            background: #fff;
            color: #333;
            float: left;
            height: 30px;
            line-height: 30px;
            margin-top: 45px;
            font-size: 13px;
        }
        .img-wrapper{
            float: left;
            margin-left: 20px;
            margin-top: 15px;
            img{
                width: 90px !important;
                height: auto;
                border: $g-box-border;
            }
        }

        .c2{
            .item-desc{
                margin-top: 20px;
                overflow: hidden;
                text-overflow: ellipsis;
                font-size: 14px;
                a{
                    color: #333;
                }
            }
            .item-title{
                color: #333;

            }
            .attr-list{
                margin-top: 10px;
                font-size: 13px;
            }
            
            li{
                float: left;
                padding: 1px 4px 1px 0px;
            }
        }
        .c3{
            padding-left: 10px;
            margin-top: 30px;
            .o-price{
                text-decoration: line-through;
                color: #aaa;
            }
        }
        .c4{
            margin-top: 40px;
        }
        .c5{
            margin-top: 40px;
        }
        .c6{
            margin-top: 40px;
        }
        .c6.remove{
            color: $g-main-color;
            cursor: pointer;
        }
    }

     /** 有效商品 row  **/
     .ok-item{
        height: 120px;
        border-bottom: $g-box-border;
        &:hover{
            background: #fcf2f1;
            
            .invalid-text{
                background: transparent !important;
            }
        }
        .c1{
            padding-left: 20px;
            position: relative;

            .invalid-text{
                padding: 1px 2px;
                background: #fff;
                color: #333;
                float: left;
                height: 30px;
                line-height: 30px;
                margin-top: 45px;
            }
            .img-wrapper{
                float: left;
                margin-left: 20px;
                margin-top: 15px;
                img{
                    width: 90px !important;
                    height: auto;
                    border: $g-box-border;
                }
            }
        }
        .c2{
            .item-desc{
                margin-top: 20px;
                overflow: hidden;
                text-overflow: ellipsis;
                font-size: 14px;
                a{
                    color: #333;
                }
            }
            .attr-list{
                // cursor: pointer;
                margin-top: 10px;
                font-size: 13px;
                color: #999;
            }
            li{
                float: left;
                padding: 1px 4px 1px 0px;
                
            }
        }
        .c3{
            padding-left: 10px;
            margin-top: 30px;
            .o-price{
                text-decoration: line-through;
                 color: #aaa;
            }
        }
        .c4{
            position:relative;
            margin-top: 40px;
            .fade-enter-active, .fade-leave-active {
                transition: opacity .5s;
            }
            .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
                opacity: 0;
            }
            .stockMax{
                margin-left:40px;
                border:1px solid #ddd;
                font-size:12px;
                width:104px;
                border: 1px solid #ffe1d3;
                border-top: 0;
                color: #f40;
                text-align: center;
                background: #fff0e7;
                p{
                    color:red;
                    font-size:14px;
                    margin: 0 10px;
                }
            }
            .stock{
                font-size:12px;
                color:#999;
                position: absolute;
                width:100%;
                left:20px;
                top:-20px;
                p{
                    text-align: center;
                }
               
            }
    
        }
        .c5.red{
            color: $g-red;
            cursor: pointer;
            margin-top: 40px;
        }

        .c6{
            margin-top: 30px;
            line-height: 12px;
            a{
                cursor: pointer;
                &:hover{
                    color: $g-main-color;
                }
            }
        }

    }
    
    
}

.check-item input[type=checkbox]:checked + label:after{
        width: 9px;
        height: 4px;
}
.g-content-box{
    .shop-bottom-bar{
        margin-top: 40px;
        height: 60px;
        line-height: 60px;
        border: $g-box-border;

        .left{
            float: left;
            font-size: 14px;
            .check-item{
                margin-left: 20px;
            }
            .mar{
                margin-left: 20px;
                cursor: pointer;
            }
        }
        .middle{
            float: right;
            padding-right: 20px;
            font-size: 0;
            span{
                margin-left: 5px;
                margin-right: 5px;
                font-size: 16px;
            }
            .grey{
                color: #999;
            }
            .red{
                color: $g-red;
            }
        }
        .right{
            float: right;
        }
    }
}


.pop-attr-box{
    width: 600px;
    padding: 20px 20px 20px 20px;
    .d-row{
        width: 100%;
        font-size: 12px;
        padding: 10px 0px 10px 10px;

        /** item 列表样式 **/ 

        .tex-o{
            float: left;
            font-size: 12px;
            padding: 5px 20px 0px 0px;
            color: #999;
        }
        .glist{                
            width: 500px;
            margin-left: 60px;
        }
        .glist li{
            float: left;
            padding-right: 10px;
            cursor: pointer;

            .item{
                padding: 5px 8px;
                color: #222222;
                font-size: 13px;
                border: #e3e3e3 solid 1px;
                display: block;
                margin-bottom: 10px;
                position: relative;

                &:hover{
                    border: $g-main-color solid 1px;
                    text-decoration: none;
                    color: $g-main-color;
                }
                img{
                    display: none;
                }
                
            }
            .item.active{
                border: $g-main-color solid 1px;

                /** 选中商品之后的小图标 **/
                img{
                    position: absolute;
                    bottom: 0;
                    right: 0;
                    width: 12px;
                    height: 12px;
                    overflow: hidden;
                    text-indent: -99em;
                    display: block;
                    background-repeat: no-repeat;
                    background-position: 0 0;
                }
            }
        }
    }

    .bottom-tool{
        margin-top: 40px;
        .b-btn{
            border: $g-box-border;
            padding: 10px 20px;
            cursor: pointer;
            background: #f8f8f8;
        }
        .b-btn.red{
            border: 1px solid $g-main-color;
            color: #fff;
            background: $g-main-color;
            margin-right: 20px;
            margin-left: 200px;
        }
    }
}
.error-text{
    color: $g-red;
}
.no-data{
    text-align: center;
    color: #333;
    font-size: 16px;
    padding: 140px 0;
    .car-pg{
        background-image: url(../../../assets/image/car.png);
        width: 76px;
        height: 64px;
        display: inline-block;
        vertical-align: middle;
    }
    span{
        display: inline-block;
        vertical-align: middle;
        text-align: left;
        line-height: 24px;
        margin-left: 10px;
    }
    
}
.is-loading{
    padding: 100px 0;
    text-align: center;
}

/*删除商品的弹框*/
.dm-txt{
    padding-left: 80px;
    text-align: left;
    .p1{
        font-size: 18px;
    }
    .p2{
        font-size: 12px;
        color: #999;
    }
}


</style>

