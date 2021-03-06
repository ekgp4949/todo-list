import { List, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React from "react";
import { call } from "../service/ApiService";
import AddTodo from "./AddTodo"
import WeeklyTodo from "./WeeklyTodo";

class WeeklyTodoList extends React.Component {
  constructor(props) {
    super(props);
    this.state = { items:[], loading: false, dayOfWeek: props.dayOfWeek };
  }
  
  add = (item) => {
    call("/todo", "POST", item).then((response) => 
      this.setState({ items: response.data })
    ).catch((error) => {
      console.log(error.error)
    });
  };

  delete = (item) => {
    call("/todo", "DELETE", item).then((response) => 
      this.setState({ items : response.data })
    ).catch((error) => {
      console.log(error.error)
    });
  };

  update = (item) => {
    call("/todo", "PUT", item).then((response) => 
      this.setState({ items: response.data })
    ).catch((error) => {
      console.log(error.error)
    });
  }

  componentDidMount() {
    call("/todo/"+this.state.dayOfWeek, "GET", null).then((response) => {
      this.setState({ items: response.data, loading: false })
    }, (error) => {
      console.log(error)
    });
  };

  render() {
    if(this.state.items.length === 0) {
      return (
        <Box>
          <Typography mb={2} variant="body1" sx={{ fontWeight: 900, color: "#c7c7c7" }}>
            할 일이 없군요 . . .
          </Typography>
          <AddTodo add={this.add} dayOfWeek={this.state.dayOfWeek} />
        </Box>
      )
    }

    return (
      <Box>
        <List
          dense
        >
          { this.state.items.map(item => 
            (<WeeklyTodo 
              key={item.id}
              item={item}
              delete={this.delete}
              update={this.update}
            />)
          ) }
        </List>
        <Box>
          <AddTodo add={this.add} dayOfWeek={this.state.dayOfWeek} />
        </Box>
      </Box>
    );
  }


}

export default WeeklyTodoList;