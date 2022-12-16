const getters = {
  isLogin: state => state.isLogin,

  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,

  mainQuestion: state => state.question.mainQuestion,
  subQuestions: state => state.question.subQuestions,
  questionList: state => state.question.questionList,
  draft: state => state.question.draft,

  // passed: state => state.batch.passed,



};

export default getters
