import { SentimentNeutral, SentimentVerySatisfied } from "@mui/icons-material";
import { Typography, Box, Stack, Avatar, Paper } from "@mui/material";
import React from "react";
import Loading from "../Loading";
import { call } from "../service/ApiService";

class SettingsScreen extends React.Component {

  constructor(props) {
    super(props);

    this.state = ({ stamps: { goodStampSrc: null, badStampSrc: null }, loading: true });
  }

  componentDidMount() {
    call("/stamps", "GET", null).then((response) => {
      console.log(response)
      this.setState({ stamps: { goodStampSrc: response.goodStampSrc, badStampSrc: response.badStampSrc }, loading: false });
    }).catch((error) => {
      console.log(error.error)
    });
  }

  changeGoodStampImg = () => {
    alert("hi1")
  }

  changeBadStampImg = () => {
    alert("hi2")
  }

  render() {
    if(this.state.loading) {
      return <Loading />;
    } else {
      const goodStamp = this.state.stamps.goodStampSrc ? 
          <Avatar sx={{ bgcolor: "green" }} alt="Good Stamp" src={ this.state.stamps.goodStampSrc } />
          : <SentimentVerySatisfied sx={{ color: "green" }} />;
      const badStamp = this.state.stamps.badStampSrc ? 
          <Avatar sx={{ bgcolor: "red" }} alt="Bad Stamp" src={ this.state.stamps.badStampSrc  } />
          : <SentimentNeutral sx={{ color: "red" }} />;
      return (
        <Box padding={3}>
          <Typography variant="header6">도장 설정</Typography>
          <Stack direction="row" mt={2} spacing={1} justifyContent="center" alignItems="center">
            <Paper onClick={this.changeGoodStampImg} sx={{ width: "100px", height: "80px", cursor: "pointer" }}>
              <Box style={{ justifyContent: "center", display: "flex", alignItems: "center", height:"70%" }}>
                { goodStamp }
              </Box>
              <Typography variant="caption" pb={0}>참 잘했어요</Typography>
            </Paper>
            <Paper onClick={this.changeBadStampImg} sx={{ width: "100px", height: "80px", cursor: "pointer" }}>
              <Box style={{ justifyContent: "center", display: "flex", alignItems: "center", height:"70%" }}>
                { badStamp }
              </Box>
              <Typography variant="caption">더 노력해요</Typography>
            </Paper>
          </Stack>
        </Box>
      );
    }

  }
}

export default SettingsScreen;