import React from "react";

import PropTypes from "prop-types";

import { FormControlLabel, Checkbox } from "@material-ui/core";
import { getColor } from "../../views/common/utils/color";
import { StyledFormControlLabel } from "./GammaCheckbox.element.styles";

const GammaCheckbox = ({
  name,
  value,
  onChange,
  onBlur,
  primary,
  secondary,
  disabled,
  label,
  error
}) => (
  <StyledFormControlLabel
    error={error}
    label={label}
    disabled={disabled}
    control={
      <Checkbox
        color={getColor(primary, secondary)}
        checked={value}
        onChange={onChange}
        onBlur={onBlur}
        name={name}
      />
    }
  />
);

GammaCheckbox.propTypes = {
  onChange: PropTypes.func.isRequired,
  label: PropTypes.string.isRequired,
  startValue: PropTypes.bool,
  primary: PropTypes.bool,
  secondary: PropTypes.bool,
  disabled: PropTypes.bool
};

export default GammaCheckbox;
