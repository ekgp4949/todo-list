import { Button, Grid, TextField } from "@mui/material";
import React from "react";

class AddTodo extends React.Component {
  constructor(props) {
    super(props);
    this.dayOfWeek = props.dayOfWeek;
    this.state = { item: { title: "", dayOfWeek: this.dayOfWeek } };
    this.add = props.add;
  }

  onInputChanged = (e) => {
    const thisItem = this.state.item;
    thisItem.title = e.target.value;
    this.setState({ item: thisItem });
  };

  onButtonClick = (e) => {
      const thisItem = this.state.item;
      if(thisItem.title.trim().length === 0) return;
      this.add(thisItem);
      this.setState({ item: { title: "", dayOfWeek: this.dayOfWeek } });
  };

  enterKeyEventHandler = (e) => {
      if(e.key === 'Enter') {
          this.onButtonClick();
      }
  };

  render() {
    return (
      <Grid container spacing={2} width="100%" className="inputContainer">
        <Grid xs={10} md={10} item>
          <TextField
            placeholder="Add Todo Here" 
            fullWidth
            size="small"
            variant="standard"
            onChange={this.onInputChanged}
            onKeyPress={this.enterKeyEventHandler}
            value={this.state.item.title}
          />
        </Grid>
        <Grid xs={2} md={2} item>
          <Button
              fullWidth
              size="small"
              color="secondary" 
              variant="outlined"
              onClick={this.onButtonClick}
          >
            +
          </Button>
        </Grid>
      </Grid>  
    )
  }
}

export default AddTodo;