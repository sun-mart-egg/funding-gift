import { FiSearch } from "react-icons/fi";

function SearchBar({}) {
  return (
    <div className="search relative mx-auto flex max-w-[95%] items-center">
      <input
        type="text"
        name="searchText"
        className="w-full rounded-md border border-cusColor3 py-1.5 pl-2 pr-10 text-gray-900 placeholder:text-xs placeholder:text-gray-400 focus:border-transparent focus:ring-2 focus:ring-cusColor3"
        placeholder="제품의 이름으로 펀딩을 검색해보세요."
      />
      <button
        type="submit"
        className="absolute inset-y-0 right-0 flex items-center px-3 text-cusColor3"
      >
        <FiSearch size={20} />
      </button>
    </div>
  );
}
export default SearchBar;
