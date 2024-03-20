import AddressList from "../component/AddressList";

function AddressListPage() {
    const data = [
        {
            name: "신시은",
            phone: "010-4948-7118",
            address: "아슬란",
            isDefault: true,
            nickname: "마이구미",
            isSelected: true,
        },
        {
            name: "박창준",
            phone: "010-2669-8294",
            address: "무지개빌",
            isDefault: false,
            nickname: "창준쓰집",
            isSelected: false,
        },
    ];
    return (
        <div>
            <AddressList listData={data} />
            <button className="bg-cusColor3 text-white">배송지 신규 입력</button>
        </div>
    );
}

export default AddressListPage;
