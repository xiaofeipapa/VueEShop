<template>
<div id="app">
        <!-- ========= 右侧内容区 begin ===================== -->
        <div class='g-content-right'>
            
            <GoodIndex ref="GoodIndex" :showTitle="false"/>
            
        </div>
        <!-- ========= 右侧内容区 end ===================== -->

        <ModalMsg ref="ModalMsg" />
</div>
</template>

<script>

import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Ajax from '@/helper/Ajax';
import ModalMsg from '@/components/com/ModalMsg';
import GoodIndex from '@/components/biz/GoodIndex';

export default {
name: 'security',
  data() {
    return {
    }
  },
  
  components: {
      ModalMsg, GoodIndex
  },
  
  //已成功挂载，相当ready()
  mounted(){
      this._loadData();
  },
  //相关操作事件
  methods: {

      _loadData(){
            let inst = this;
            inst.$nextTick(_ => {
                inst.$refs.GoodIndex.loadLikeGoods();

            });
      }, 
     
  },
  computed:{
      
  },
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .g-content-right{
        float: left;
        width: 958px;
        margin-left: 40px;
        
        .row{
            line-height: 50px;

            input{
                margin-left: 40px;

                @include g-input;
            }

            .label{
                color: #666;
            }
            .error{
                color: $g-red;
                margin-left: 20px;
            }

            .btn {
                @include g-button;
                margin-left: 150px;
            }
        }
    }

</style>
