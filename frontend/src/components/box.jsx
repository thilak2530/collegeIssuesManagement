import React from "react";

function Box(props){
    return(
        <div className={props.class}>
            <p className={props.class1}>{props.script}</p>
            <p className={props.clsas2}>{props.number}</p>
        </div>
    );
}

export default Box;