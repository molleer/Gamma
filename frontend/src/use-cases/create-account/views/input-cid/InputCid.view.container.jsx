import { connect } from "react-redux";
import { createAccountValidateCid } from "../../CreateAccount.action-creator";
import { toastOpen } from "../../../../app/views/gamma-toast/GammaToast.view.action-creator";
import InputCid from "./InputCid.view";

const mapStateToProps = (state, ownProps) => ({});

const mapDispatchToProps = dispatch => ({
  showError: text => dispatch(toastOpen({ text: text })),
  sendCid: cid => dispatch(createAccountValidateCid(cid))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InputCid);
