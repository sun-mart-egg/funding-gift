function Card({title, name, date, progress}) {
    return (
        <div className="bg-white rounded-lg shadow-md p-4 m-2 w-2/5 border-2">
            <div className="product_img_div"><img src="" alt="" /></div>
            <h5 className="product_title">{title}</h5>
            <p>{progress}%</p>
            <p className="product_des">{name}</p>
            <div className="product_mon">{date}</div>  
            {
                progress ===100
                ? <p>finished</p>
                : <p></p>
            }                
        </div>
    );
}

export default Card