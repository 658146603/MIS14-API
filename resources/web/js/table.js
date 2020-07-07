$ = jQuery

class Table {
    constructor(id, col) {
        this.id = id
        this.col = col
    }

    removeAll() {
        $('#' + this.id + ' tr:not(:first)').remove();
        mdui.updateTables()
    }

    addAll(items) {
        items.forEach((item) => {
            let tr = document.createElement('tr')
            this.col.forEach(key => {
                let value = item[key]
                let td = document.createElement('td')
                td.innerHTML = value
                tr.appendChild(td)
            })
            $('#' + this.id).append(tr)
        })

        mdui.updateTables()
    }
}

class Selector {
    constructor(id, col) {
        this.id = id
        this.col = col
        this.inst = new mdui.Select('#' + this.id)
        this.data = []
    }

    removeAll() {
        $('#' + this.id).empty()
        this.inst.handleUpdate()
    }

    init() {
        $('#' + this.id).append('<option selected="selected" value="-1">请选择</option>')
        this.data.forEach((item) => {
            $('#' + this.id).append('<option value="' + item[this.col[0]] + '">' + item[this.col[1]] + '</option>')
        })
        this.inst.handleUpdate()
    }

    curr() {
        let target = document.getElementById(this.id)
        let index = target.selectedIndex
        return target.options[index].value
    }

    update(data) {
        this.data = data
        this.removeAll()
        this.init()
    }
}