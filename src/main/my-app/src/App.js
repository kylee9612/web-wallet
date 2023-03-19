import React from 'react';
import "./components/css/index.css"
import Header from "./components/page/js/common/header";
import Body from "./components/page/js/xrp/body";
import Footer from "./components/page/js/common/footer";
import Deposit from "./components/page/js/xrp/deposit";

function App() {

  return (
      <div id={"inner_root"}>
          <Header/>
          <Deposit coin={"xrp"}/>
          {/*<Footer/>*/}
      </div>
  );
}

export default App;