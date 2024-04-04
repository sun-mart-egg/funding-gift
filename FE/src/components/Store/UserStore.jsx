import { create } from "zustand";

const useUserStore = create((set) => ({
  id: null,
  email: "",
  name: "",
  profileImageUrl: "",
  phoneNumber: "",
  birthday: "",
  birthyear: "",
  gender: "",
  detailAddr: "",
  defaultAddr: "",
  zipCode: "",

  // 상태를 업데이트하는 함수
  updateUserStore: (key, value) =>
    set((state) => ({
      ...state,
      [key]: value,
    })),

  // 상태 초기화 함수
  resetUserStore: () =>
    set({
      id: null,
      email: "",
      name: "",
      profileImageUrl: "",
      phoneNumber: "",
      birthday: "",
      birthyear: "",
      gender: "",
      detailAddr: "",
      defaultAddr: "",
      zipCode: "",
    }),
}));

export default useUserStore;
