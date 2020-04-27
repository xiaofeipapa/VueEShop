<template>
<div class="max-width-wrapper">
    
    <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
    <TopUserSpan />     
    <div style="clear:both"></div>

    <TopAdSpan />
    <div style="clear:both"></div>

    <TopBannerSearch/>
    <div style="clear:both"></div>

    <TopNav />
    <div style="clear:both"></div>

    <HomeFloor />
    <div style="clear:both"></div>

    <GoodIndex ref="GoodIndex"/>
    <div style="clear:both"></div>

    <BottomArticle />
    <div style="clear:both"></div>

    <BottomLink />
    <div style="clear:both"></div>

</div>
</template>

<script>

    import Vue from 'vue'
    import TopUserSpan from '@/components/biz/TopUserSpan';
    import TopAdSpan from '@/components/biz/TopAdSpan';
    import TopBannerSearch from '@/components/biz/TopBannerSearch';
    import TopNav from '@/components/biz/TopNav';
    import GoodIndex from '@/components/biz/GoodIndex';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import HomeFloor from './HomeFloor';
    import store from '@/store'
    import { mapState, mapMutations, mapGetters } from 'vuex'

    

export default {
  store, 
  data() {
    return {
        
    }
  },
  components: {
        TopUserSpan, TopAdSpan , TopBannerSearch, TopNav, GoodIndex, BottomArticle
        , BottomLink, HomeFloor
      
  },
  //实例初始化最之前，无法获取到data里的数据
  beforeCreate(){
  	
  	
  },  
  //在挂载开始之前被调用
  beforeMount(){
      
      
  
  }, 
  //已成功挂载，相当ready()
  mounted(){
      let inst = this;
      inst.$nextTick(_ => {

          inst.$refs.GoodIndex.loadData();

      });
  },
  updated(){
      
  },
  watch:{
  },
  //相关操作事件
  methods: {
    
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .max-width-wrapper{
        width: 100%;
        margin: 0;
    }
   
   
</style>
