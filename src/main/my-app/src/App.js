import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Main from "./components/page/js/main";
import "./components/css/index.css"

function App() {
  const [hello, setHello] = useState('')

  return (
      <div>
        <Main/>
        {/*백엔드에서 가져온 데이터입니다 : {hello}*/}
      </div>
  );
}

export default App;