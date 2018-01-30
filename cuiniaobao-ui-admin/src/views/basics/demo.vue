<template>
  
  <div class="app-container calendar-list-container">
    <div class="oprate">
      <span @click="addNewOne">添加记录</span>
    </div>
  	<el-main>
      <el-table :data="mallList">
        <el-table-column prop="mallCode" label="日期" width="140">
        </el-table-column>
        <el-table-column prop="mallName" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="mallShortName" label="地址">
        </el-table-column>
        <el-table-column prop="mallArea" label="面积">
        </el-table-column>
        <el-table-column prop="mallListpic" label="图片">
        </el-table-column>
        <el-table-column prop="bottomPrice" label="底价">
        </el-table-column>
      </el-table>
    </el-main>
    <el-main>
      <el-table :data="cityList">
        <el-table-column prop="cityCode" label="城市代码" width="140">
        </el-table-column>
        <el-table-column prop="cityName" label="城市名" width="120">
        </el-table-column>
        <el-table-column prop="cityStatus" label="状态">
        </el-table-column>
        <el-table-column label="编辑">
        </el-table-column>
      </el-table>
    </el-main>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="temp" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('table.platformName')" prop="title">
          <el-input v-model="temp.title"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.platformPhone')" prop="title">
          <el-input v-model="temp.companyPhone"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.platformSite')" prop="title">
          <el-input v-model="temp.companySite"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.platformDesc')">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="Please input" v-model="temp.remark">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{$t('table.cancel')}}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{$t('table.confirm')}}</el-button>
        <el-button v-else type="primary" @click="updateData">{{$t('table.confirm')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import mallApi from '../../api/demo.js'
  export default {
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }
      return {
        dialogFormVisible: false,
        tableData: Array(3).fill(item),
        mallList: null,
        cityList: null,
        textMap: {
          update: 'Edit',
          create: 'Create'
        },
        temp: {
          id: undefined,
          importance: 1,
          remark: '',
          timestamp: new Date(),
          title: '',
          type: '',
          status: 'published'
        },
        queryParam: {
          pageNo: 1,
          pageSize: 20,
          orderType: null,
          searchMap: {
            mallCode: null,
            mallName: this.$route.query.mallName,
            cityCode: null,
            districtCode: null,
            businessregionCode: this.$route.query.businessregionCode,
            malltypeCode: null,
            mallAreaFrom: null,
            mallAreaTo: null,
            dayPriceFrom: null,
            dayPriceTo: null,
            areakindId: this.$route.query.areakindId,
            purposeId: null
          }
        }
      }
    },
    mounted() {
      this.getCityList()
      this.getMoreMallList()
    },
    methods: {
      /** 查询商业体列表 */
      getMoreMallList() {
        mallApi.getMallList(this.queryParam).then(response => {
          if (response.errorCode === 1) {
            this.mallList = response.data.list
          } else {
            this.error.errorCode = response.errorCode
            this.error.errorContent = response.errorContent
          }
        })
      },
      getCityList() {
        mallApi.getCityList().then(response => {
          if (response.errorCode === 1) {
            this.cityList = response.data
          }
        })
      },
      addNewOne() {
        console.log(1)
      },
      handleFilter() {
        this.listQuery.page = 1
        this.getList()
      },
      handleSizeChange(val) {
        this.listQuery.limit = val
        this.getList()
      },
      handleCurrentChange(val) {
        this.listQuery.page = val
        this.getList()
      },
      handleModifyStatus(row, status) {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        row.status = status
      },
      resetTemp() {
        this.temp = {
          id: undefined,
          importance: 1,
          remark: '',
          timestamp: new Date(),
          title: '',
          status: 'published',
          type: ''
        }
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
            this.temp.author = 'vue-element-admin'
            createArticle(this.temp).then(() => {
              this.list.unshift(this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.temp.timestamp = new Date(this.temp.timestamp)
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const tempData = Object.assign({}, this.temp)
            tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
            updateArticle(tempData).then(() => {
              for (const v of this.list) {
                if (v.id === this.temp.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.temp)
                  break
                }
              }
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleDelete(row) {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      },
      handleFetchPv(pv) {
        fetchPv(pv).then(response => {
          this.pvData = response.data.pvData
          this.dialogPvVisible = true
        })
      },
      handleDownload() {
        this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
          const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
          const data = this.formatJson(filterVal, this.list)
          excel.export_json_to_excel(tHeader, data, 'table-list')
          this.downloadLoading = false
        })
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
      }
    }
  }
</script>
<style>
.oprate{float:right;}
.oprate span{color:#fc0;}
</style>
