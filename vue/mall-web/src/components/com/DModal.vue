<template>
<transition name="fade">
  <div class="modal-mask modal-transition"
     @click="checkHide($event)"
    v-show="isShow">
    <div class="modal-wrapper">
      <div class="modal-container"  
        ref='dmodel'
        :style="'width:'+width+'px;height:'+height+'px;'">
              <div class="modal-header">
                <slot name='header'>
                    
                </slot>
              </div>
              <div class="modal-body">
                <slot name='body'></slot>
              </div>
      </div>
    </div>
  </div>
</transition>
</template>

<script>
/** 
 * ========= 通用模台对话框 ============= 
 * 
 * //TODO : 加上空白处点击关闭模态框的功能. 
 */

import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';

export default {
    props: {

        // 宽度
        width: {type: Number, required: false,}, 

        // 高度
        height: {type: Number, required: false,}, 

    }, 
  data() {
    return {
        isShow: false, 

        // _width: 400, 
        // _height: 320, 
    }
  },
  
  //在挂载开始之前被调用
  beforeMount(){

      let inst = this;
      // inst._width = inst.width ? inst.width: 600; 
      // inst._height = inst.height ? inst.height: 400; 
      inst._checkHide = inst.closeWhenClickOutsideModal 
        ? inst.closeWhenClickOutsideModal: true; 
  
  }, 
  //已成功挂载，相当ready()
  mounted(){
    
	
  },
  //相关操作事件
  methods: {

  	checkHide(event) {
        
        let checkDiv = this.$refs.dmodel;

        let isIn = VueHelper.isMouseInObject(event, checkDiv);

        if( ! isIn && this._checkHide){
            this.hide();
        }

        // console.log('------- checkHide');
    },     
    
    show() {
          this.isShow = true;
      }, 
    hide() {
          this.isShow = false;
          this.$emit('onHide');
      }, 
	      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

  .modal-mask{
      background-color: rgba(0, 0, 0, .5);
      position: fixed;
      width: 100%;
      height:100%;
      top:0;
      left: 0;
      z-index: 1000;
      display: table;
  }
  .modal-wrapper{
      display: table-cell;
      vertical-align: middle;
  }
  .modal-container{
      margin: auto;
      top: 50%;
      background-color: white;
      border-radius: 5px;
      overflow: hidden;
    //   padding: 10px 10px 10px 10px;
  }
  .modal-default-button{
      float: right;
  }

  .fade-enter-active{
    transition: opacity .4s;
  }
  .fade-leave-active {
    transition: opacity .1s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
    .modal-header h3{
        margin-top: 0;
        color:#333;
    }
    .modal-body{
        // margin: 20px 0;
    }

</style>
