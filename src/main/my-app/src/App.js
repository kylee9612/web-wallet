import React from 'react';
import "./components/css/index.css"
import Header from "./components/page/js/common/header";
import Body from "./components/page/js/xrp/body";
import Footer from "./components/page/js/common/footer";

function App() {

  return (
      <div id={"inner_root"}>
          <Header/>
          <Body/>
          <Footer/>
      </div>
  );
}

export default App;