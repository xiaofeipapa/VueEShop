<template>
    <div class="g-mask-bg"
        v-show="isShow"
        > 
        
        <div class="g-center-show"> 
            <img src='../../assets/image/loading.gif' />{{text}}
        </div>

    </div>
</template>

<script>

import * as VueHelper from '@/helper/VueHelper';

// 这个组件使用的场合: 当执行某些服务器操作的时候,需要背景全灰, 等待服务器返回. 
export default {
  name: 'LoadingScreen',
  
  data () {
    return {
        text: '正在加载,请稍候......',
        isShow: false, 
    }
  }
  ,  
  //在挂载开始之前被调用
  beforeMount(){
  
  }, 

  methods: {
      show: function(){
          this.isShow = true;
      }, 
      hide: function(){
          this.isShow = false;
      }, 
  }
  ,computed: {
      
  }, 
}
</script>

<style lang="scss" scoped type="text/css">

    .g-mask-bg{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0px;
        left: 0px;
        z-index: 9999;
        background: rgba(#999, 0.4);

        .g-center-show{
            padding: 10px;
            position: absolute;
            top: 0;
            left: 0;
            color: #333;
            background: #fff;
            font-size: 16px;
            border-radius: 2px;
            margin: auto;
            bottom: 0;
            right: 0;
            width: 200px;
            height: 23px;
            text-align: center;
            img{
                margin-right: 10px;
            }
            border-radius: 2px;
        }
    }
    .g-mask-bg.show{
        display: 'block';
    }
    .g-mask-bg.hide{
        display: none;
    }


</style>
