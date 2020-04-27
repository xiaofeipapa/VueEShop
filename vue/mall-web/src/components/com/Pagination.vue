<template>

    <div class="pagination-wrap">
        <span class="pages">
          <a class="prev" :class="{'invalid':pageNo==1}" @click="_changePage(parseInt(pageNo)-1)">上一页</a>
          <a class="page"
          v-for="page in pageCount"
          v-if="Math.abs(parseInt(pageNo) - parseInt(page)) <= 2"
          :key="page.id" 
          :class="{'current':pageNo==page}"
          @click="_changePage(page)"

          >{{page}}</a>
          <a class="next" :class="{'invalid':pageNo==pageCount}" @click="_changePage(parseInt(pageNo)+1)">下一页</a>
        </span>
        <span class="total-count">共{{pageCount}}页</span>
        <span>到第&nbsp;&nbsp;<input type="text" class="inp" 
          @input="_check"
          v-model="currentPage">&nbsp;&nbsp;页&nbsp;&nbsp;</span>
        <span class="confirm" @click="_changePage(currentPage)">确定</span>
    </div>

</template>

<script>

// 分页
export default {
  name: 'Pagination',
  props:['pageCount','pageNo'],
  data () {
    return {
        currentPage:'',
    }
  },
  beforeMount(){

    
  }, 
  methods: {
    _check(){
      let v = this.currentPage.replace(/\D/g, '');
      if(v < 0)v = 1;
      if(v > this.pageCount)v = this.pageCount;

      this.currentPage = v;
    }, 
    _changePage(pageNo){
      if(pageNo <= this.pageCount && pageNo >= 1){
        this.$emit('pageNo',pageNo);
      }
      
    }
      
  }

}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

.pagination-wrap{
  clear: both;
  overflow: hidden;
  text-align: center;
  padding-top: 40px;
  padding-bottom: 40px;
  font-size: 12px;
  > span{
    vertical-align: middle;
  }
  .pages{
    border: solid 1px #ccc;
    overflow: hidden;
    display: inline-block;
    a{
      width: 45px;
      height: 42px;
      text-align: center;
      line-height: 42px;
      float: left;
      border-right: solid 1px #ccc;
      cursor: pointer;
    }
    .current{
      font-weight: 400;
      color: $g-main-color;
      font-size: 16px;
    }
    .next,.prev{
      padding: 0 15px;
      &.invalid{
        cursor: not-allowed;
        color: #ccc;
      }
    }
    .next{
      border-right:0 none;
    }
  } 
  .total-count{
    margin-left: 10px;
    margin-right: 20px;
  }
  .inp{
    width: 40px;
    padding: 10px 5px;
    border: solid 1px #ccc;
    text-align: center;
  }
  .confirm{
    cursor: pointer;
    width: 48px;
    height: 40px;
    display: inline-block;
    text-align: center;
    line-height: 40px;
    color: #fff;
    background-color: $g-main-color;
  }



}




</style>
