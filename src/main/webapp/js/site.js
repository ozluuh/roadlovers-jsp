document.addEventListener('DomContentLoaded', function (event) {
  const element = document.querySelector("input[data-type='currency']");

  element.addEventListener('keypress', e => formatCurrency(e.target));
  element.addEventListener('change', e => formatCurrency(e.target));

  function formatCurrency(input) {
    const formatNumber = n => {
      // format number 1000000 to 1,234,567
      return n.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    // get input value
    let input_val = input.value.trim();
    console.log(input);
    // don't validate empty input
    if (input_val === '') {
      return;
    }

    // original length
    // let original_len = input_val.length;

    // initial caret position
    // let caret_pos = input.getAttribute('selectionStart');

    // check for position of first decimal
    // this prevents multiple decimals from
    // being entered
    let decimal_pos = input_val.indexOf('.');

    if (decimal_pos >= 0) {
      // split number by decimal point
      let left_side = input_val.substring(0, decimal_pos);
      let right_side = input_val.substring(decimal_pos);

      // add commas to left side of number
      left_side = formatNumber(left_side);

      // validate right side
      right_side = formatNumber(right_side);

      // On blur make sure 2 numbers after decimal
      //   if (blur === 'blur') {
      //     right_side += '00';
      //   }

      // Limit decimal to only 2 digits
      right_side = right_side.substring(0, 2);

      // join number by .
      input_val = 'R$ ' + left_side + '.' + right_side;
      input.value = input_val;
      return;
    }

    // no decimal entered
    // add commas to number
    // remove all non-digits
    input_val = formatNumber(input_val);
    input_val = 'R$ ' + input_val;

    // final formatting
    input_val += '.00';
    input.value = input_val;
  }
});
