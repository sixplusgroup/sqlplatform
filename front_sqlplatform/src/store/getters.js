const getters = {
  isLogin: state => state.isLogin,

  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userStars: state => state.user.userStars,
  statistic: state => state.user.statistic,
  recentSubmit: state => state.user.recentSubmit,

  mainQuestion: state => state.question.mainQuestion,
  subQuestions: state => state.question.subQuestions,
  questionList: state => state.question.questionList,
  draft: state => state.question.draft,


};

export default getters
