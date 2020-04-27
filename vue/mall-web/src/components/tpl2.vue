<template>
<div id="app">
    
    
</div>
</template>

<script>

import * as Ajax from '@/helper/Ajax';
import 'font-awesome/css/font-awesome.min.css';

import BottomPage from 'components/BottomPage';
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';

export default {
  data() {
    return {
        
        hotBrandC1: [], 
    }
  },
  components: {
       BottomPage
  },
  //实例初始化最之前，无法获取到data里的数据
  beforeCreate(){
  	
  	
  },  
  //在挂载开始之前被调用
  beforeMount(){

    //   let inst = this;
      
    //   // 加载数据
    //     Lib.M.ajax({
    //         'url': Urls.get_front_page_data,
    //         'success':function (server_data){
                
    //             var data = server_data.data;
    //             console.log(data);

    //             // inst.hotBrandC1 = data.hotBrandC1; 
                
    //         }
    //     });
  
  }, 
  //已成功挂载，相当ready()
  mounted(){
  
  
	
  },
  //相关操作事件
  methods: {
    
    // 跳转到品牌首页页面
	// gotoBrandIndex : function(brandId){
    //     window.location = '/views/brand/index.html?aaa=kkk&brand=' + brandId;
    // }, 
	
      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';



</style>
