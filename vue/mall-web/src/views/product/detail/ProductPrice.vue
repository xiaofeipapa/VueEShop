<template>
    <!-- 商品价格-->
    <div class="d-row"> 

        <span class='label-2'>零售价</span>
        <span class='currency' v-show="retailPrice">￥{{Number(retailPrice).toFixed(2)}}</span>
        
        <span class='o-price' v-show="marketPrice">￥{{Number(marketPrice).toFixed(2)}}</span>
    </div> 
</template>

<script>

import * as VueHelper from '@/helper/VueHelper';
import store from '@/store'
import { mapState, mapMutations, mapGetters } from 'vuex'

export default {

  store,
    
  data() {
    return {
        userCat: 'ClientBuyer',       // 用户类型
        userGrade: 'normal',          // 用户等级
        marketPrice: '',        // 市场价
        retailPrice: '',        // 零售价, 实际显示价格
    }
  },
    computed: {

    }, 
  components: {
      
  },
  //相关操作事件
  methods: {

      setData(data){

          // 判断等级, 改变label 或者 price

          this.retailPrice = data.retailPrice;
          this.marketPrice = data.marketPrice;
      },

      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .d-row{

        padding: 3px 0;

        .label-2{
            color: #333;
            margin-right: 20px;
        }
        .currency{
            font-size: 24px;
            color: $g-red;     
            
        }
        .o-price{
            margin-left: 20px;
            font-size: 13px;
            color: #999;
            text-decoration: line-through;     
        }
    }
        
      

</style>
