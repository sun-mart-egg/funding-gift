import Card from "../component/Card";

function CardList({ data }) {
  return (
    <div className="m-1 flex flex-wrap justify-start">
      {data.map((data, index) => (
        <Card
          key={index}
          title={data.title}
          productName={data.productName}
          startDate={data.startDate}
          endDate={data.endDate}
          progress={0}
          img={data.productImage}
        />
      ))}
    </div>
  );
}

export default CardList;
