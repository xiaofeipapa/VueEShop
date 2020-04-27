<template>
<transition name="fade">
<div class="txt" v-if="isShow">{{msg}}</div>
</transition>
</template>

<script>

export default {
    components: {
    },
    props: {
        
    },
    data() {
        return {        
            isShow:false,
            msg: '', 
        }
    },
    mounted(){
        
    },
    watch:{
        msg(newValue){
            this.msg = newValue;
        }
    },
    //相关操作事件
    methods: {
        show(msg) {
            this.msg = msg;
            let inst = this;
            this.isShow = true;
            setTimeout(function(){
                inst.hide();
            },2000)
        }, 
        hide() {
            this.isShow = false;
            this.msg = '';
        },
        
        
    }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

.txt{
    position: fixed;
    padding: 8px 30px;
    margin: auto;
    top: 50%;
    left: 50%;
    background-color: rgba(0,0,0,0.5);
    text-align: center;
    line-height: 20px;
    color: #fff;
    border-radius: 20px;
    transform: translate(-50%,-50%);
    font-size: 13px;
    z-index: 10000;
}
.fade-enter-active{
    transition: opacity .4s;
  }
  .fade-leave-active {
    transition: opacity .1s;
  }
  .fade-enter, .fade-leave-to  {
    opacity: 0;
  }

 


</style>
