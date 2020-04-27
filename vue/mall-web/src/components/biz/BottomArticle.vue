<template>
    <div class="actual-box">
        <div class='each-box' v-for="data in dataList" :key="'article-' + data.id">
            <div class="title-box">
                {{data.name}}
            </div>
            <div class="article"
                @click="_goToHelp"
                 v-for="art in data.children" :key="'art-' + art.id">
                {{art.name}}
            </div>
        </div>  

    </div>
</template>

<script>

// =============== 底部文章分类
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import * as Ajax from '@/helper/Ajax';


export default {
  name: 'app',

  data () {
    return {
        dataList: [

        ], 
    }
  }, 
    components: {
                    
    }, 

    methods: {

      _goToHelp(){
          window.location.href = "/views/common/help.html";
      }, 


        indexSearch (){
            
            let inst = this;

            let url = Urls.homeIndex_getBottomArticles;
            let params = {
            }
            
            Ajax.post(url, params, function(serverData){
                    inst.dataList = serverData.data;
            });
        }, 
    }, 

    mounted(){
        this.indexSearch();
    }, 
}
</script>

<style lang="scss" scoped>

    .actual-box {
        width: 1250px;
        margin: 0 auto; 
        display: flex;
        flex-direction: row;

        .each-box{
            width: 200px;
            padding: 10px 15px 10px 15px;
            border: 1px solid #fff;
            border-radius: 2px;
            margin-bottom: 20px;
            

        }

        .title-box{
            font-weight: bold;
            margin-bottom: 5px;    
            color: #666;        
        }
        .article{
            color: #666;
            font-size: 14px;
            line-height: 24px;
            height: 24px;
            cursor: pointer;
            &:hover{
                color: firebrick;
            }
        }
    }
    
    
</style>
