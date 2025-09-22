import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import MyThemeProvider from "./MyThemeProvider.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <MyThemeProvider>
            <App/>
        </MyThemeProvider>
    </StrictMode>,
)
