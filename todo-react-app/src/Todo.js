import React from "react";
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from "@material-ui/core"
import { DeleteOutlined } from "@material-ui/icons";

class Todo extends React.Component {
    constructor(props) {
        super(props);

        this.state = { item: props.item, readOnly: true };
        this.delete = props.delete;
        this.checkItems = props.checkItems;
    }


    deleteEventHandler = () => {
        this.delete(this.state.item);
    }

    offReadOnlyMode = () => {
        this.setState({ readOnly: false })
    };

    enterKeyEventHandler = (e) => {
        if(e.key === 'Enter') {
            this.setState({ readOnly: true });
            this.checkItems();
        }
    };

    editEventHandler = (e) => {
        const thisItem = this.state.item;
        thisItem.title = e.target.value;
        this.setState({ item: thisItem });
    };

    checkBoxEventHandler = () => {
        const thisItem = this.state.item;
        thisItem.done = !thisItem.done;
        this.setState({ item: thisItem });
        this.checkItems();
    };

    render() {
        const item = this.state.item;
        return (
            <ListItem>
                <Checkbox 
                    checked={item.done} 
                    onChange={this.checkBoxEventHandler}
                />
                <ListItemText>
                    <InputBase 
                        inputProps={{ 
                            "aria-label" : "naked",
                            readOnly: this.state.readOnly 
                        }}
                        type="text"
                        id={item.id}
                        name={item.id}
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                        onClick={this.offReadOnlyMode}
                        onKeyPress={this.enterKeyEventHandler}
                        onChange={this.editEventHandler}
                    />
                </ListItemText>
                <ListItemSecondaryAction>
                    <IconButton aria-label="Delete Todo" onClick={this.deleteEventHandler}>
                        <DeleteOutlined/>
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        );
    }
}

export default Todo;