<template>
    <div id="app">
        <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
        <TopUserSpan />     
        <div style="clear:both"></div>

        <TopBannerSearch ref="TopBannerSearch"/>
        <div style="clear:both"></div>
        
        <GoodIndex ref="GoodIndex" :showTitle="false"/>
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
    import GoodIndex from '@/components/biz/GoodIndex';
    import Toolbar from '@/components/biz/Toolbar';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    

export default {
  data() {
    return {
        
    }
  },
  store,
  components: {
      TopUserSpan, TopBannerSearch, GoodIndex, 
      Toolbar, BottomArticle, BottomLink, 

  },
  
  mounted(){

      // 解析页面参数
      let queryUrl = VueHelper.parseUrlQueryString();
    //   console.log('--- queryUrl: ', queryUrl);

      let keyword = VueHelper.getUrlParam(queryUrl, 'q');
      if(keyword){
          keyword = decodeURI(keyword);
      }
    //   console.log('--- keyword: ', keyword);
            
      let inst = this;
      inst.$nextTick(_ => {

          inst.$refs.TopBannerSearch.setKey(keyword);
          inst.$refs.GoodIndex.loadData(queryUrl);

      });
  },
  //相关操作事件
  methods: {
      
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

