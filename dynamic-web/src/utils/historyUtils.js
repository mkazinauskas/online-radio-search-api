export const reloadPage = (history) => {
    const curentLocation = history.location.pathname + history.location.search
    history.push(curentLocation)
}