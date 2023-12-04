const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

lass App extends React.Component { (1)

	constructor(props) {
		super(props);
		this.state = {employees: []};
	}

	componentDidMount() { (2)
		client({method: 'GET', path: '/api/employees'}).done(response => {
			this.setState({employees: response.entity._embedded.employees});
		});
	}

	render() { (3)
		return (
			<EmployeeList employees={this.state.employees}/>
		)
	}
}