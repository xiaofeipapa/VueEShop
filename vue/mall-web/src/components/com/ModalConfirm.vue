<template>
<DModal ref='DModal' :width="width" :height="height">
        <div slot="header" class="dm-title">{{title}}<span class="close" @click="hide">×</span></div>
        <div slot="body" class="dm-content">   
            <slot name="content"></slot>
            <button class="btn sure" v-if="okText" @click="ok">{{okText}}</button>
            <button class="btn cancel" v-if="cancelText" @click="cancel">{{cancelText}}</button>
        </div>
    </DModal>
</template>

<script>

/*
--可设置只显示一个确认按钮， okText 为空时即只显示一个按钮
--确认和取消按钮均可设置监听回调函数，确认回调为 ok(),取消为cancel()
--点击确认和取消会默认关闭模态框，无需另外执行关闭
*/

import DModal from 'components/com/DModal';

export default {
    props: {

        // 宽度
        width: {type: Number, default: 300}, 

        // 高度
        height: {type: Number, default: 120,}, 
        title:"",                                       //弹框的标题
        okText:"",                                     //确认按钮的文字
        cancelText:{type: String, required: false, default: '取消'},     //取消按钮的文字

    }, 
  data() {
    return {
    }
  },
  components: {
        DModal,
  },
  //在挂载开始之前被调用
  beforeMount(){

  }, 
  //已成功挂载，相当ready()
  mounted(){
  },
  //相关操作事件
  methods: {
      show() {
          this.$refs.DModal.show();
      }, 
      hide() {
          this.$refs.DModal.hide();
      }, 
      ok(){
          this.$emit("ok");
          this.hide();
      },
      cancel(){
          this.$emit("cancel");
          this.hide();
      },

	      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

.dm-title{
    background-color: #f8f8f8;
    line-height: 33px;
    padding: 0 15px;
    font-size: 14px;
    .close{
        font-size: 20px;
        top: -2px;
        cursor: pointer;
        float: right;
        position: relative;
    }
}
.dm-content{
    padding-top: 20px;
    text-align: center;
    font-size: 14px;
    .dm-txt{
        margin-bottom:20px;
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
    button{
        background-color: transparent;
        border: solid 1px #ddd;
        padding: 8px 18px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 3px;
        margin-top: 20px;
    }
    .sure{
      background-color: $g-main-color;
      color: #fff;
    }
    .cancel{
        margin-left: 18px;
        
        
    }
}
  

</style>
