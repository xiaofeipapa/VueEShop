<template>

<DModal
    :width='360'
    :height='360'
    ref='modalLogin'>
    

    <div class='g-box-wrapper' 
            slot="body">
        <LoginWidget ref="LoginWidget" @loginSucc="_loginSucc"/>
    </div>

</DModal>
</template>

<script>

    import Vue from 'vue'
    import GetIdentifyCode from 'components/biz/GetIdentifyCode';
    import DModal from 'components/com/DModal';
    import * as Urls from '@/helper/Urls';
    import * as Ajax from '@/helper/Ajax';
    import LoginWidget from 'components/biz/LoginWidget';
    

export default {
    
  data() {
    return {
        callback: '', 
    }
  },
  components: {
        GetIdentifyCode, DModal, LoginWidget
      
  },
  watch:{
      
  },
  computed:{
      
  },
  //相关操作事件
  methods: {

      _loginSucc(){
          this.hide();
          if(this.callback){
              this.callback();
          }
      }, 

        // 设置登录成功后的回调函数
      setCallback(callback){
          this.callback = callback
      },

	show() {

        this.$refs.modalLogin.show();

    }, 
	hide() {
        this.$refs.modalLogin.hide();
    }, 
      
      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';
    
   
</style>
