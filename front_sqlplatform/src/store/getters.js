const getters = {
  isLogin: state => state.isLogin,

  token: state => state.user.token,
  userId: state => state.user.userId,
};

export default getters
