<template>
<!-- 商品详情-->
	<div class='g-product-wrapper'>
        <div class='g-product-box'>   
                <p class="crumb">
                    <a>首页</a><i class="fa fa-angle-right"></i>
                    <!-- <a href=""></a><i class="fa fa-angle-right"></i>
                    <a href=""></a><i class="fa fa-angle-right"></i>
                    <a href=""></a><i class="fa fa-angle-right"></i>-->
                    <a>{{product.brandName}}</a><i class="fa fa-angle-right"></i> 
                    <span class="product-label">{{product.modalTitle}}</span>
                </p>  
                <!-- 左侧放大镜图片-->
                <div class="showbot"> 
                    <Magnify :previewImg="currentImage.normal" 
                            :zoomImg="currentImage.big"></Magnify>

                    <!--展示图片盒子--> 
                    <div class="small-img-box invalid-left" :class="showImages.length <= 6 ? 'invalid-right' :''" ref="smallImgBox"> 
                        
                        <span class="position-box">
                            <span class="ul" ref="smallImg">
                                <img 
                                v-for="item in showImages" 
                                :key="item.thumb"
                                :src="item.thumb+'?x-oss-process=image/resize,m_fill,w_50,h_50'"
                                v-bind:class="item.showing ? 'showing': ''"
                                @mouseover="_changeShowImage($event, item)"
                                />
                            </span>
                        </span>       
                        
                    </div> 
                    <div class="follow">
                        <span v-if="!likeGoods" @click="_setLikeGoods(true)">关注商品</span>
                        <template v-else>
                        <i class='fa fa-heart'></i> 
                        <span @click="_setLikeGoods(false)">已关注商品</span>
                        </template>
                    </div>
                </div>
                <!-- 中间商品属性-->
                <div class="tb-property"> 
                    <div class="tr-nobdr"> 
                        {{product.modalTitle}} &nbsp;&nbsp;{{product.specValueString}}
                    </div> 
                    <div class="price-content" >                        
                        <ProductPrice ref="ProductPrice"/>
                    </div>
                    <!-- 选择规格 -->                 
                    <div class="d-row mb-10"
                        v-for="(item,index) in groups" 
                        :key="item.id"
                    > 
                        <span class="tex-o">{{item.name}}</span> 
                        <ul class="glist" id="glist" >
                            <li
                                v-for="attrValue in item.children" 
                                :key="attrValue.id"
                                >
                                
                                    <div :title="attrValue.name" 
                                        class="item"
                                        @click="_getProductId(attrValue)"
                                        v-bind:class="attrValue.check ? 'check':''"
                                        
                                        >{{attrValue.value}}
                                        
                                    </div>


                            </li> 
                        </ul> 
                    </div> 
                    <div style='clear:both'></div>

                    <!-- 库存和数量 -->
                    <div class="d-row mb-10"> 
                        <span class="tex-o">数量</span> 
                        <input type="button" class="qubtn first" value="-"
                            @click="_changeQuantity(false)">
                        <input type="text" v-model="quantity" class="quvalue" 
                            autocomplete="off"
                            @keyup="quantity=quantity.replace(/[^\d]/g,'')" ng-pattern="/[^a-zA-Z]/" @blur="quantity = quantity < 1 ? 1 : quantity"/> 
                        <input type="button" class="qubtn" value="+"
                         @click="_changeQuantity(true)">
                         <span class="qulast" v-if="quantity >= 100">最多只能购买100个商品</span>
                    </div>
                     
                    <div style='clear:both'></div>

                    <div class="d-row mb-10"> 
                        <span class="tex-o">&nbsp;</span> 
                        <template v-if="product.dataStatus == 'notSale'">
                        <button class="buy-now-btn invalid">该商品已下架</button>
                        </template>
                        <template v-else>
                        <span class="error-buy">{{errorMsgForBuy}}</span>
                        <button class="add-cart-btn" style="position:  relative;" 
                            @click="_addToCart($event)">加入购物车</button> 
                        </template>
                    </div>
                    <div style='clear:both'></div>
                    <div class="t-tip">温馨提示：请仔细核对商品等数据, 不支持7天无理由退货</div>
                    <div class="t-guarantee">联系我们：400-888-9999</div> 


                </div>

                <!-- 右侧看了又看-->
                <div class="right-box invalid-up" ref="rightSeeBox">
                    
                    
                </div>
        </div>

        <ModalAddCart ref="ModalAddCart" />
    </div>
</template>

<script>

    // 包括: 左侧放大镜, 中间商品选择, 右则区域. (暂时空白)


import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import Magnify from 'components/com/Magnify';
import ModalAddCart from 'components/biz/ModalAddCart';

import store from '@/store'
import { mapState, mapMutations, mapGetters } from 'vuex'
import ProductPrice from './ProductPrice';
import * as Ajax from '@/helper/Ajax';

export default {
  store,
    
  data() {
    return {
        
        product: {
            'brandName': '', 
        }, 
        showImages: [],         // 放大镜的5张图片
        currentImage: {},       // 正在放大镜显示的图片
        groups: [],             // 一系列的商品分类-属性对应关系. 可选的属性
        likeGoods: false,       // 是否关注

        currentSpecMap: {

        },          // 用于判断是否选择所有规格属性, 从而跳到另一个产品

        quantity: 1,        // 购买数量

        errorMsgForBuy: '', 
    }
  },
    computed: {

    }, 
  components: {
      Magnify, ProductPrice, ModalAddCart
  },
  
  //已成功挂载，相当ready()
  mounted(){
  
  
	
  },
  //相关操作事件
  methods: {

      setData(data){
          this.product = data;

          this._initShowImages(data);
          
          this.$refs.ProductPrice.setData(data);

          this._initCheckSpec(data);

          this.currentSpecMap = data.currentSpecMap;

          this.likeGoods = data.likeGoods;

        //   console.log('--- data.currentSpecMap: ', data.currentSpecMap);

      },

      // 设置规格属性勾选
      _initCheckSpec(data){
          this.groups = data.groups;

      },

        // 放大镜图片
      _initShowImages(data){

          let inst = this;

            //当前选定属性的主图，放在第一张
            let showImages = data.modalImages.split(',');
            if(showImages && showImages.length > 0){
                let list = []
                showImages.forEach(url => {
                    let new_data = {
                        'big': url, 
                        'normal': url, 
                        'showing': false, 
                        'thumb': url, 
                    }
                    list.push(new_data);
                });

                let curr = list[0];
                curr.showing = true;

                inst.showImages = list;
                inst.currentImage = curr;
            }
        
      }, 

    // 改变放大镜的显示图片
	_changeShowImage : function(event, item){
        this.showImages.forEach(element => {
            element.showing = false;
        });
        this.currentImage = item;
        this.currentImage.showing = true;
    }, 

    //关注/取消关注商品
    _setLikeGoods(flag){
        let inst = this;
        let toSet = !this.likeGoods;

        let url = Urls.product_likeGood;
        let params = {
            'pid':inst.product.id,
            'flag': flag, 
        }

        Ajax.post(url, params, function(serverData){
            if(serverData.sessionExpire == 'Y'){
                inst.$refs.modelLogin.show();
                return;
            }
            
            inst.likeGoods = toSet;
        });
    },

    // 跳到另一个商品
    _getProductId(attrValue){

        // console.log('--- attrValue: ', attrValue);

        /*        
            逻辑: 商品可能有多个规格属性, 只有多个规格属性都选中一个的时候, 才会进行跳转. 
         */
        let inst = this;
        let gid = attrValue.groupId;        // 规格组id
        let vid = attrValue.id;             // 规格属性id

        let existVid = this.currentSpecMap[gid];

        if(vid == existVid){
            // 点击本已选定的属性
            return;
        }

        // 否则表示选择了其他属性, 需要跳到另一个商品
        let vids = [vid];
        for(var p in this.currentSpecMap){

            // 其他的规格属性
            if(p != gid){
                vids.push(this.currentSpecMap[p]);
            }
        }

        // console.log('--- before submit: ', this.currentSpecMap);

        // 查找产品id, 然后跳转
        let url = Urls.product_findPidBySpec;
        let params = {
            'jsonData': JSON.stringify(
                {
                    'modalId': this.product.modalId, 
                    'specIds': vids, 
                }
            )
        }

        Ajax.post(url, params, function(serverData){
            let pid = serverData.data;
            // console.log("-- productId: " , pid);
            
            window.location.href = "/views/product/detail.html?pid=" + pid;
        });


    },
    // 加减属性
	_changeQuantity : function(isAdd){

        let q = this.quantity;

        if(isAdd){
            if(q<100){
                q++;
            }
        }else{
            if(q > 1){
                q--;
            }
        }

        this.quantity = q;

    }, 

    ...mapMutations('shopStore', [
        'addProduct'
    ]),

    // 加入购物车
    _addToCart(event){

        let pid = this.product.id;
        let modalTitle = this.product.modalTitle;
        let specValueString = this.product.specValueString;

        let payload = {
            'pid': pid, 
            'modalTitle': modalTitle, 
            'specValueString': specValueString, 
            'quantity': this.quantity, 
            'retailPrice': this.product.retailPrice, 
            'productImage': this.product.productImage, 
        }
        // console.log('--- payload: ', payload);
        
        // 修改store数据
        this.addProduct(payload);

        this.$refs.ModalAddCart.show(payload);


    }, 
    	
      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    @mixin text-overflow-ellipsis{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .g-product-wrapper{
        width: 100%;  

        .g-product-box{
            @include g-page-content-row;    

        }
    }

    .crumb{
        line-height: 45px;
        a{
            color: #999999;
        }
        i{
            margin: 0 7px;
            font-size: 14px;
            color: #999;
        }
        .product-label{
            width: 200px;
            display: inline-block;
            @include text-overflow-ellipsis;
            height: 16px;
            line-height: 13px;
            vertical-align: middle;
        }
    }

    .showbot{
          float: left;
          
          .small-img-box{
              margin-top: 10px;
              overflow: hidden; 
              height: 52px; width: 100%; 
              position: relative;
              font-size: 0;
              &.invalid-left .fa-angle-left{
                  color: #ece8e8;
              }
              &.invalid-right .fa-angle-right{
                  color: #ece8e8;
              }
              .position-box{
                  height: 52px;
                  display: inline-block;
                  vertical-align: middle;
                  width: 337px;
                  overflow: hidden;
                  position: relative;
                  .ul{
                      width: 1000px;
                      position: absolute;
                      left: 0;
                      transition:all 0.5s ease;
                  }
              }
              .fa{
                  font-size: 38px;
                  width: 32px;
                  text-align: center;
                  line-height: 52px;
                  cursor: pointer;
                  vertical-align: middle;
              }
              .fa-angle-right{
                  float: right;
              }
              img{
                  width: 50px !important;
                  height: 50px !important;
                  border: 1px solid #f8f8f8;
                  margin-right: 5px;
                  cursor: pointer;
                  &:hover{
                      border: 1px solid $g-main-color;                        
                  }
              }

              .showing{
                  border: 1px solid $g-main-color; 
              }
          }
          .follow{
              margin-top: 15px;
              margin-left: 12px;
              .i{
                color: $g-main-color
              }

              span{
                  cursor: pointer;
              }
          }
      }

      
        .tb-property{
            // width: 480px;
            // margin-left: 50px;
            // float: left;
            // padding-right: 40px;
            width: 580px;
            margin-left: 20px;
            float: left;
            padding-right: 20px;
            .tr-nobdr{
                font-size: 18px;
                margin: 5px;
                margin-bottom: 10px;
            }

        }
        .price-content{
            padding: 15px 10px;
        }
       
        .d-row{
            padding: 3px 10px;
            margin-bottom: 10px;

            .mb-10{
                margin-bottom:10px ;
            }

            /** item 列表样式 **/ 
            .tex-o{
                float: left;
                color: #666;
                width: 50px;
                padding-top: 6px;
                padding-right: 5px;
            }
            .glist{            
                margin-left: 55px;
            }
            .glist li{
                float: left;
                padding-right: 10px;
                cursor: pointer;
                margin-bottom: 10px;
                .item{
                    padding: 7px 8px;
                    color: #222222;
                    font-size: 14px;
                    border: #e3e3e3 solid 1px;
                    display: block;
                    position: relative;
                    border-radius: 2px;

                    &:hover{
                        border: $g-main-color solid 1px;
                        text-decoration: none;
                        color: $g-main-color;
                    }
                    
                    &.check {
                        border: $g-main-color solid 1px;
                        text-decoration: none;
                        color: $g-main-color;
                    }
                    
                }
            }

        }
      
            /** 数量 **/
            .qubtn{
                padding: 0 8px;
                background: transparent;
                border: #e3e3e3 solid 1px;
                font-size: 16px;
                cursor: pointer;
                height: 30px;
                line-height: 30px;
                vertical-align: middle;
            }
            .quvalue{
                width:30px; 
                text-align: center; 
                color: #0F0F0F;
                border: #e3e3e3 solid 1px;
                padding: 0 16px;
                height: 28px;
                line-height: 28px;
                vertical-align: middle;
            }
            .qulast{
                margin-left: 10px;
                color: #ff0000;
                font-size: 13px;
            }

            /** 立即购买 / 加入购物车 **/
            @mixin add-btn-comm{
                margin-right: 10px;
                font-size:18px;
                line-height: 36px;
                border: $g-main-color solid 1px;
                cursor: pointer;
                margin-top: 20px;
                border-radius: 2px;
            }
            .buy-now-btn{
                @include add-btn-comm;
                background:transparent;
                color: $g-main-color;
                width: 126px;
                &.invalid{
                    color: #999;
                    border-color: #ccc;
                    pointer-events: none;
                    cursor: not-allowed;
                    
                }
            }
            .add-cart-btn{
                @include add-btn-comm;
                background: $g-main-color no-repeat 10px center;
                font-weight: bold;
                color: #FFFFFF;
                text-align: center;
                padding: 5px 20px;
                
                &.invalid{
                    background-color: #999;
                    border-color: #ccc;
                    pointer-events: none;
                    cursor: not-allowed;
                }
            }
            .error-buy{
                color: $g-main-color;
                position: absolute;
            }
        .t-tip{
            color: #999;
            margin-top: 15px;
            line-height: 30px;
        }
        .t-guarantee{
            color: #999;
            line-height: 30px;
        }

</style>
