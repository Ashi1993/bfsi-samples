import "./ActionArea.css"
import {ManagePayeeIcon, PayBillsIcon, ScheduleIcon, TransferFundsIcon} from "../../../svg_module/SvgModule.jsx";
import {QuickActionButton} from "../../components/AppCommonComponents.jsx";
import {useContext} from "react";
import ConfigContext from "../../../context/ConfigContext.jsx";


const onclickAction = ()=>{
    alert("Quick action Button clicked")
}

const quickActions = [
    { icon: PayBillsIcon, name: "Pay Bills", onClick: onclickAction },
    { icon: TransferFundsIcon, name: "Transfer", onClick: onclickAction },
    { icon: ScheduleIcon, name: "Schedule", onClick: onclickAction },
    { icon: ManagePayeeIcon, name: "Payees", onClick: onclickAction }
];


const ActionArea = ()=>{

    const{userinfo} = useContext(ConfigContext);



    return (
        <>
            <div className="actions-outer">
                <div className="profile-section">
                    <div className="profile-image"></div>
                    <div className="greeting-and-name">
                        <p>Hello,</p>
                        <p className="greeting-second-para">{userinfo.name}, Good Evening !</p>
                    </div>
                </div>
                <div className="quick-action-area">

                    {quickActions.map((action,index)=>(
                        <QuickActionButton key={index} icon={< action.icon/>} name={action.name} onClick={action.onClick}/>
                    ))}

                </div>
            </div>
        </>
    )
}

export default ActionArea;