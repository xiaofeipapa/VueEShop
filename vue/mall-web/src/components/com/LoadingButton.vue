<template>
    <button class="btn-class"
        @click="handleClick"
        v-bind:class="isLoading ? 'is-btn-loading': 'danger'"
        
        >   
        <span v-if="isLoading">正在处理...</span>
        <span v-if="$slots.default && !isLoading"><slot></slot></span>
    </button>
</template>

<script>

// =============== 底部文章分类
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Ajax from '@/helper/Ajax';


export default {
  props: {
      
    },

  data () {
    return {
        isLoading: false, 
    }
  }, 
    components: {
                    
    }, 
    computed: {
        
    },

    methods: {
        handleClick(evt) {
            this.isLoading = true;
            this.$emit('click', evt);
        }, 

        startLoading(){
            this.isLoading = true;
            // console.log('--- startLoading: ', this.isLoading);
        }, 

        closeLoading(){
            this.isLoading = false;
            // console.log('--- closeLoading: ', this.isLoading);
        }, 

    }, 

    mounted(){
        
    }, 
}
</script>

<style lang="scss" scoped>

    @import '../../assets/scss/themeDefault.scss';

    .btn-class{
        width: 120px;
        font-size: 20px;
        text-align: center;
        cursor: pointer;
        border: none;
        border-radius: 2px;
        height: 50px;

        &.danger{
            background: $g-main-color;
            color: #fff;
        }

        &.is-btn-loading{
            background: #999;
            color: #fff;
        }
    }

    
    
</style>
