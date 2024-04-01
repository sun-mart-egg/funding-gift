import Card from "../component/Card";

function CardList({ data }) {
  console.log("Data received in CardList component:", data);

  return (
    <div className="m-1 my-2 flex flex-wrap justify-start">
      {data.map((item, index) => (
        <Card
          key={index}
          title={item.title}
          productName={item.productName}
          startDate={item.startDate}
          endDate={item.endDate}
          progress={0}
          img={item.productImage}
        />
      ))}
    </div>
  );
}

export default CardList;
