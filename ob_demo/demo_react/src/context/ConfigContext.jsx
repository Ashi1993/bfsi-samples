import {createContext, useEffect, useState} from "react";

const ConfigContext = createContext({});

export const ConfigProvider = ({children}) => {

    const [userinfo, setUserInfo] = useState({name: "", image:"/"});


    // const [loading, setLoading] = useState(true);

    useEffect(() => {

        const loadConfigs = async () => {

            try{
                const response = await fetch("/configurations/config.json");
                const data = await response.json();

                setUserInfo(data.user);
            }catch (error) {
                console.error(error);
            }



        }

        loadConfigs();



    },[])


    return (<ConfigContext.Provider value = {{userinfo}} >{children}</ConfigContext.Provider>);
}

export default ConfigContext;