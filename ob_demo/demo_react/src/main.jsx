import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import MyThemeProvider from "./MyThemeProvider.jsx";
import {BrowserRouter} from "react-router-dom";
import {ConfigProvider} from "./context/ConfigContext.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>

        <ConfigProvider>
            <BrowserRouter>
                <MyThemeProvider>
                    <App/>
                </MyThemeProvider>
            </BrowserRouter>
        </ConfigProvider>
    </StrictMode>,
)
