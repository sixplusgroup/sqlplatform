const getters = {
  isLogin: state => state.isLogin,

  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,

  mainQuestion: state => state.question.mainQuestion,
  subQuestions: state => state.question.subQuestions,

};

export default getters
