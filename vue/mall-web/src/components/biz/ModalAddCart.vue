<template>
<DModal
    :width='600'
    ref='modelBox'
        >
        <div class='g-done-shop-box'
            slot="body"
        >   
        <span class="close" @click="hide()">×</span>
        <!-- 正常状态 -->
        <template v-if="showSucc">
            
            <div class='row-1'>                       
                    <div class="img-box">
                        
                    </div> 
                    <div class="text-box">已成功加入购物车</div>
            </div>
            <div class='clear-both'></div>
            <div class='row-2'>   
                    <div class="c1">
                        <img :src="buyProduct.productImage" />
                    </div> 
                    <div class="c2">
                            <div class="r1">
                                {{buyProduct.modalTitle}}
                            </div>
                            <div class="r2">
                                <!-- 随机选4-5个属性放这里 -->
                              {{buyProduct.specValueString}}
                            </div>
                            <div class="r3">
                                数量 : <span>{{buyProduct.quantity}}</span>
                            </div>
                    </div> 
            </div>
            <div class='clear-both'></div>
            <div class='row-3'>   
                <div class="r1"
                    @click="_continueShop()"
                >返回继续购物</div>
                <div class="r2"
                    @click="_goShopcart()"
                
                >去购物车结算</div>
            </div>
        </template>
        <!-- 异常状态 -->
        <template v-else>
            <div class='row-1'>                       
                    <div class="img-box">
                        <img src='../../assets/image/error.png'>
                    </div> 
                    <div class="text-box error">加入购物车失败</div>
            </div>
            <div class='clear-both'></div>
            <div class='row-4'>加入购物车失败, 原因: {{errorMsg}}</div>
            <div class='clear-both'></div>
            <div class='row-3'>   
                <div class="r1"
                    @click="_continueShop()"
                >返回继续购物</div>
            </div>
        </template>
        <div class='clear-both'></div>
        </div>
    </DModal>
</template>

<script>
/** 
 * ========= 加入购物车之后弹出的模态框 ============= 
 * 有两种状态: 失败或成功. 具体逻辑配合 productDetailApp 来阅读. 
 */

import * as Ajax from '@/helper/Ajax';
import DModal from 'components/com/DModal';
    import * as Events from '@/helper/Events';

export default {
  components: {
      DModal
  },
    props: {

    }, 
  data() {
    return {        
        // 已买商品
        buyProduct: {
            label: '公牛插座独立开关电源插排插线插座包邮', 
            attrLabel: '优质铜芯导线 导电好 发热少', 
            quantity: 1, 
        },  // 已购买商品

        showSucc: true,   // 成功或失败界面
        errorMsg: '', 
    }
  },
  
  //已成功挂载，相当ready()
  mounted(){
  
  
	
  },
  //相关操作事件
  methods: {
    
	show(data) {
        this.buyProduct = data;
        this.showSucc = true;
        this.$refs.modelBox.show();

    }, 
	hide() {
        this.$refs.modelBox.hide();
    }, 


    // 继续购物
    _continueShop(){
        // 查询库存并更新购物车数据. 如果成功, 显示模态框
        this.$refs.modelBox.hide();
    }, 
    // 到购物车页面
    _goShopcart(){
        this.$refs.modelBox.hide();
        window.location.href = '/views/product/shopcart.html';
    }, 
	      
  }
}
</script>
<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

/** 成功加入购物车 **/
.g-done-shop-box{
    width: 600px;
    padding-top: 40px;
    padding-bottom: 40px;
    position: relative;
    .close{
        position: absolute;
        right: 0px;
        text-align: center;
        font-size: 27px;
        line-height: 36px;
        top: 0px;
        cursor: pointer;
        color: #333;
        width: 41px;
    }
    .row-1{
        width: 100%;
        height: 50px;
        font-size: 24px;
        color: #71b247;
        padding-left: 40px;
        .img-box{
            margin-right: 10px;
            display: inline-block;
            vertical-align: middle;
        }
        .text-box{
            display: inline-block;
            vertical-align: middle;
        }
        .text-box.error{
            color: #333;
        }
    }
    .row-2,.row-4{
        padding: 20px 40px;
        overflow: hidden;
        .c1{
            float: left;
            img{
                width: 140px;
                height: 128px;
            }
        }
        .c2{
            margin-left: 160px;

            .r1{
                margin-bottom: 10px;
            }
            .r2{
                margin-bottom: 10px;
                color: #666;
            }

        }
    }
    .row-4{
        display: block;
    }
    .row-3{
        margin-top: 10px;
        .r1{
            font-size: 18px;
            padding: 15px 0;
            background: #fff;
            cursor: pointer;
            border: 1px solid #eaeaea;
            width: 200px;
            margin: 0 auto;
            text-align: center;
            display: inline-block;
            margin-left: 40px;
        }
        .r2{
            display: inline-block;
            font-size: 18px;
            padding: 15px 30px;
            color: #fff;
            background: $g-main-color;
            cursor: pointer;
            margin-left: 40px;
        }
    }
}

</style>
