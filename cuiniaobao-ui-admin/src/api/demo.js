import instance from '.'

const api = {}

export default api

// 根据catId获取单个文章信息
api.getCityList = (data) => {
  return instance.get('v2/bas/city/openCities', { params: data })
}

// 根据articleId获取单个文章信息
api.getArticleInfo = (data) => {
  return instance.get('v2/ftm/adInfo/list', { params: data })
}
// get mall list
api.getMallList = (data) => {
  return instance.post('/v3/mall/list', data)
}
