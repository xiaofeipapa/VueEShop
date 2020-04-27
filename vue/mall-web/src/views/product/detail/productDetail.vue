<template>
    <div id="app">
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <TopBannerSearch />
        <div style="clear:both"></div>

        <ProductContent ref="ProductContent" @addCart="_addCart"/>     
        <div style="clear:both"></div>

        <Toolbar ref="Toolbar"/>     
        <div style="clear:both"></div>

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
    import store from '@/store'
    
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import TopBannerSearch from '@/components/biz/TopBannerSearch';
    import Toolbar from '@/components/biz/Toolbar';
    import ProductContent from './ProductContent';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    

export default {
  data() {
    return {
        
    }
  },
  store,
  components: {
      TopUserSpan, TopBannerSearch, 
      ProductContent, Toolbar, BottomArticle, BottomLink, 

  },
  
  //在挂载开始之前被调用
  beforeMount(){

      let inst = this;

      // 解析页面参数
      let queryString = VueHelper.parseUrlQueryString();
      let url = Urls.product_detail + '?' + queryString;
      
        Ajax.get(url, {}, function(serverData){
              let data = serverData.data;
              inst.$refs.ProductContent.setData(data);
        });
  
  }, 
  mounted(){

  },
  //相关操作事件
  methods: {

      _addCart(payload){
          let target = payload.target; 
          let quantity = payload.quantity;

          this.$refs.Toolbar.drop(target, quantity);
      }, 
      
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .g-product-wrapper{
        width: 100%;  

        .g-product-box{
            @include g-page-content-row;    

        }
    }

</style>

