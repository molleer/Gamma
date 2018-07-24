import { connect } from "react-redux";
import ShowAllUsers from "./ShowAllUsers.screen";
import loadTranslations from "../../../../common/utils/loaders/translations.loader";
import translations from "./ShowAllUsers.screen.translations";

const mapStateToProps = (state, ownProps) => ({
  text: loadTranslations(
    state.localize,
    translations.ShowAllUsers,
    "Users.Screen.ShowAllUsers."
  ),

  users: state.users
});

const mapDispatchToProps = dispatch => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShowAllUsers);
