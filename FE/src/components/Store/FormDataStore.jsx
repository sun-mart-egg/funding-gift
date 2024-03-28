// formDataStore.js
import create from "zustand";

const useFormDataStore = create((set) => ({
  formData: {
    minPrice: null,
    targetPrice: 76000,
    anniversaryDate: "",
    startDate: "",
    endDate: "",
    title: "",
    content: "",
    accountBank: "",
    accountNo: "",
    name: "신시은",
    phoneNumber: "",
    defaultAddr: "",
    detailAddr: "",
    zipCode: "",
    isPrivate: false,
    anniversaryCategoryId: 1,
    productId: 3,
    productOptionId: 1,
  },

  // formData 상태를 업데이트하는 함수
  updateFormData: (key, value) =>
    set((state) => ({
      formData: {
        ...state.formData,
        [key]: value,
      },
    })),

  // 상태 초기화 함수
  resetFormData: () =>
    set({
      formData: {
        minPrice: null,
        targetPrice: 760000,
        anniversaryDate: "",
        startDate: "",
        endDate: "",
        title: "",
        content: "",
        accountBank: "",
        accountNo: "",
        name: "신시은",
        phoneNumber: "",
        defaultAddr: "",
        detailAddr: "",
        zipCode: "",
        isPrivate: true,
        anniversaryCategoryId: 1,
        productId: 1,
        productOptionId: 1,
      },
    }),
}));

export default useFormDataStore;
